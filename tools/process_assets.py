#!/usr/bin/env python
import json
import shutil
import sys, os

def try_locate_texpacker_jar():
    dirs_to_check = ['./','bin/','jars/']
    if sys.platform == 'linux':
        dirs_to_check += ['/usr/bin/', '/usr/local/bin', os.path.expanduser('~/.gdxtools/bin')]
    elif sys.platform == "win32":
        dirs_to_check += [os.path.join(os.getenv("USERPROFILE"), r"AppData\Local\libgdxtools\bin"), os.path.join(os.getenv("PROGRAMFILES"), r"libgdxtools\bin")]

    for d in dirs_to_check:
        if os.path.exists(d):
            if "runnable-texturepacker.jar" in os.listdir(d):
                return os.path.join(d, "runnable-texturepacker.jar")

def process_namespaced_location(loc):
    return loc.split("::")

def ns_rel_path(p, r):
    ns, rl = process_namespaced_location(p)
    return os.path.join(ns, r, fix_path(rl))

def fix_path(p: str):
    if '/' in p and sys.platform == 'win32':
        return p.replace('/', '\\')
    elif '\\' in p and sys.platform != 'win32':
        return p.replace('\\', '/')
    return p


def build_atlas(asset_path, texpacker_jar):
    print(f"Building atlas {asset_path}")
    atlas_path = ns_rel_path(asset_path, "atlases") + ".atlas.json"
    with open(atlas_path, 'r') as f:
        jsd = json.load(f)

    inc = jsd["include"]
    inc_path = ns_rel_path(inc, "pack_textures")

    sns = process_namespaced_location(asset_path)[0]
    out_path = os.path.join(sns, "atlases", fix_path('.'.join(os.path.basename(atlas_path).split('.')[:-2]))) + os.path.sep
    if not os.path.exists(out_path):
        os.makedirs(out_path, exist_ok=True)

    BASECMD = f"java -cp \"{texpacker_jar}\" com.badlogic.gdx.tools.texturepacker.TexturePacker {inc_path} {out_path}"
    print(BASECMD)
    os.system(BASECMD)

PROCESSABLE_ASSET_TYPES = [
    "atlas"
]



if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Process Assets. Run this from the assets directory")
    parser.add_argument('-A','--atlas', action='append')
    parser.add_argument('--find-atlases', action='store_true')
    parser.add_argument('-a', '--asset-dir', default='.')
    v = parser.parse_args(sys.argv[1:])

    os.chdir(v.asset_dir)

    tp = try_locate_texpacker_jar()

    atls = (v.atlas or [])[:]

    if v.find_atlases:
        import glob
        ass = glob.glob("*/atlases/*.atlas.json")
        atls += [(f.split(os.path.sep))[0] + '::' + '.'.join(os.path.basename(f).split('.')[:-2]) for f in ass]

    for atl in atls:
        build_atlas(atl, tp)
