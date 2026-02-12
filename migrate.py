#!/usr/bin/env python3
"""
Migration script: Restructure Java project to standard Maven/Gradle layout
with com.dragon root package and domain-based grouping.
"""

import os
import re
import shutil
from pathlib import Path

PROJECT_ROOT = Path("/home/thomson/thonk/project/server-indie")
OLD_SRC = PROJECT_ROOT / "src"
NEW_SRC = PROJECT_ROOT / "src/main/java/com/dragon"

# Mapping: old top-level package → new path relative to com/dragon/
PACKAGE_MAP = {
    "boss":       "boss",
    "clan":       "model/clan",
    "combine":    "content/combine",
    "consts":     "consts",
    "daihoi":     "content/daihoi",
    "data":       "core/data",
    "database":   "core/database",
    "dev1sme":    "content/dev1sme",
    "dungeon":    "content/dungeon",
    "event":      "content/event",
    "interfaces": "core/interfaces",
    "intrinsic":  "content/intrinsic",
    "item":       "model/item",
    "managers":   "managers",
    "map":        "model/map",
    "matches":    "content/matches",
    "mob":        "model/mob",
    "network":    "core/network",
    "npc":        "model/npc",
    "player":     "model/player",
    "radar":      "content/radar",
    "server":     "core/server",
    "services":   "services",
    "shop":       "content/shop",
    "skill":      "model/skill",
    "system":     "system",
    "task":       "content/task",
    "utils":      "utils",
}

def get_new_package(old_package: str) -> str:
    """Convert old package name to new package name."""
    parts = old_package.split(".")
    top_level = parts[0]
    if top_level in PACKAGE_MAP:
        new_base = "com.dragon." + PACKAGE_MAP[top_level].replace("/", ".")
        if len(parts) > 1:
            return new_base + "." + ".".join(parts[1:])
        return new_base
    return old_package  # unchanged if not in map

def get_new_dir(old_rel_dir: str) -> str:
    """Convert old relative directory to new relative directory under com/dragon/."""
    parts = old_rel_dir.split(os.sep)
    top_level = parts[0]
    if top_level in PACKAGE_MAP:
        new_base = PACKAGE_MAP[top_level]
        if len(parts) > 1:
            return os.path.join(new_base, *parts[1:])
        return new_base
    return old_rel_dir

def collect_java_files(src_dir: Path):
    """Collect all .java files with their relative paths."""
    files = []
    for root, dirs, filenames in os.walk(src_dir):
        for f in filenames:
            if f.endswith(".java"):
                full_path = Path(root) / f
                rel_path = full_path.relative_to(src_dir)
                files.append((full_path, rel_path))
    return files

def collect_resource_files(src_dir: Path):
    """Collect all non-.java resource files."""
    files = []
    for root, dirs, filenames in os.walk(src_dir):
        for f in filenames:
            if not f.endswith(".java"):
                full_path = Path(root) / f
                rel_path = full_path.relative_to(src_dir)
                files.append((full_path, rel_path))
    return files

# Build sorted replacement list (longest prefixes first to avoid partial matches)
def build_import_replacements():
    """Build list of (old_pattern, new_pattern) for import/package replacements,
    sorted by length descending to prevent partial matches."""
    replacements = []
    for old_pkg, new_path in PACKAGE_MAP.items():
        new_pkg = "com.dragon." + new_path.replace("/", ".")
        replacements.append((old_pkg, new_pkg))
    # Sort by old package name length descending
    replacements.sort(key=lambda x: len(x[0]), reverse=True)
    return replacements

def update_java_content(content: str, replacements: list) -> str:
    """Update package declaration and import statements in Java file content."""
    lines = content.split("\n")
    new_lines = []
    
    for line in lines:
        stripped = line.strip()
        
        # Update package declaration
        if stripped.startswith("package "):
            match = re.match(r'^(\s*)package\s+([\w.]+)\s*;', line)
            if match:
                indent = match.group(1)
                old_pkg = match.group(2)
                new_pkg = get_new_package(old_pkg)
                line = f"{indent}package {new_pkg};"
        
        # Update import statements
        elif stripped.startswith("import "):
            is_static = "import static " in stripped
            if is_static:
                match = re.match(r'^(\s*)import\s+static\s+([\w.]+)\s*;', line)
                if match:
                    indent = match.group(1)
                    old_import = match.group(2)
                    new_import = transform_import(old_import, replacements)
                    line = f"{indent}import static {new_import};"
            else:
                match = re.match(r'^(\s*)import\s+([\w.*]+)\s*;', line)
                if match:
                    indent = match.group(1)
                    old_import = match.group(2)
                    new_import = transform_import(old_import, replacements)
                    line = f"{indent}import {new_import};"
        
        new_lines.append(line)
    
    return "\n".join(new_lines)

def transform_import(old_import: str, replacements: list) -> str:
    """Transform an import path using the replacement map."""
    for old_pkg, new_pkg in replacements:
        # Match exact package or package prefix (with dot)
        if old_import == old_pkg:
            return new_pkg
        if old_import.startswith(old_pkg + "."):
            return new_pkg + old_import[len(old_pkg):]
    return old_import  # no change for external imports (java.*, javax.*, etc.)

def main():
    print("=" * 60)
    print("Java Project Restructuring Migration")
    print("=" * 60)
    
    # Step 0: Backup check
    backup_dir = PROJECT_ROOT / "src_backup"
    if backup_dir.exists():
        print(f"\nBackup already exists at {backup_dir}")
        print("Remove it first if you want to re-run migration.")
        return
    
    # Step 1: Collect all Java files from current src/
    print("\n[1/6] Collecting Java files...")
    java_files = collect_java_files(OLD_SRC)
    print(f"  Found {len(java_files)} Java files")
    
    # Step 2: Collect resource files
    print("\n[2/6] Collecting resource files...")
    resource_files = collect_resource_files(OLD_SRC)
    print(f"  Found {len(resource_files)} resource files")
    
    # Step 3: Read all file contents
    print("\n[3/6] Reading all Java files...")
    file_contents = {}
    for full_path, rel_path in java_files:
        with open(full_path, "r", encoding="utf-8") as f:
            file_contents[rel_path] = f.read()
    
    resource_contents = {}
    for full_path, rel_path in resource_files:
        with open(full_path, "rb") as f:
            resource_contents[rel_path] = f.read()
    
    # Step 4: Build replacements
    print("\n[4/6] Building import replacement map...")
    replacements = build_import_replacements()
    for old, new in replacements:
        print(f"  {old} → {new}")
    
    # Step 5: Backup old src/
    print(f"\n[5/6] Backing up src/ to {backup_dir}...")
    shutil.copytree(OLD_SRC, backup_dir)
    
    # Step 6: Remove old src/ and create new structure
    print("\n[6/6] Creating new structure and writing files...")
    shutil.rmtree(OLD_SRC)
    
    # Write Java files to new locations
    written = 0
    for rel_path, content in file_contents.items():
        # Determine new path
        rel_dir = str(rel_path.parent)
        new_rel_dir = get_new_dir(rel_dir)
        new_dir = NEW_SRC / new_rel_dir
        new_file = new_dir / rel_path.name
        
        # Update content
        new_content = update_java_content(content, replacements)
        
        # Write
        new_dir.mkdir(parents=True, exist_ok=True)
        with open(new_file, "w", encoding="utf-8") as f:
            f.write(new_content)
        written += 1
    
    # Write resource files to src/main/resources/
    resources_dir = PROJECT_ROOT / "src" / "main" / "resources"
    for rel_path, content in resource_contents.items():
        new_file = resources_dir / rel_path
        new_file.parent.mkdir(parents=True, exist_ok=True)
        with open(new_file, "wb") as f:
            f.write(content)
    
    print(f"\n  Migrated {written} Java files")
    print(f"  Migrated {len(resource_contents)} resource files")
    
    # Summary
    print("\n" + "=" * 60)
    print("Migration complete!")
    print("=" * 60)
    print(f"\nNew source root: src/main/java/com/dragon/")
    print(f"Backup saved to: src_backup/")
    print(f"\nRemember to update build.gradle:")
    print(f"  - Remove custom sourceSets (use Gradle default)")
    print(f"  - Update mainClass to 'com.dragon.core.server.ServerManager'")
    print(f"\nTo verify: ./gradlew compileJava")
    print(f"To rollback: rm -rf src/ && mv src_backup/ src/")

if __name__ == "__main__":
    main()
