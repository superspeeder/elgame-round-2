import sys
import argparse
import os
import requests
import shutil

TPSOURCEURL = "https://libgdx-nightlies.s3.eu-central-1.amazonaws.com/libgdx-runnables/runnable-texturepacker.jar"

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Install LibGDX Tools")
    parser.add_argument("--system", action="store_true")
    parser.add_argument("--dry-run", action="store_true")
    parser.add_argument("--uninstall", action="store_true")
    parser.add_argument("--uninstall-all", action="store_true")

    a = parser.parse_args(sys.argv[1:])

    if a.dry_run:
        print("Simulating Installation of LibGDX Tools")

    if a.uninstall_all:
        for p in [os.path.join(os.getenv("USERPROFILE"), r"AppData\Local\libgdxtools"), os.path.join(os.getenv("PROGRAMFILES"), r"libgdxtools")]:
            if os.path.exists(p):
                if not a.dry_run:
                    shutil.rmtree(p,)
                print(f"Delete {os.path.abspath(p)}")

        print("Uninstalled LibGDX Tools")
        exit(0)

    if a.uninstall:
        if a.system:
            p = os.path.join(os.getenv("PROGRAMFILES"), r"libgdxtools\bin")
        else:
            p = os.path.join(os.getenv("USERPROFILE"), r"AppData\Local\libgdxtools")
        if os.path.exists(p):
            if not a.dry_run:
                shutil.rmtree(p)
            print(f"Delete {os.path.abspath(p)}")

        print("Uninstalled LibGDX Tools")
        exit(0)



    store_dir = None

    if a.system:
        if sys.platform == "win32":
            store_dir = os.path.join(os.getenv("PROGRAMFILES"), r"libgdxtools\bin")
        elif sys.platform == "linux":
            store_dir = "/usr/local/bin"
    else:
        if sys.platform == "win32":
            store_dir = os.path.join(os.getenv("USERPROFILE"), r"AppData\Local\libgdxtools\bin")
        elif sys.platform == "linux":
            store_dir = os.path.expanduser("~/.gdxtools/bin")

    if not a.dry_run:
        os.makedirs(store_dir, exist_ok=True)

        with open(os.path.join(store_dir, "runnable-texturepacker.jar"), 'wb+') as f:
            r = requests.get(TPSOURCEURL, allow_redirects=True)
            f.write(r.content)
            print(f"Installed runnable-texturepacker.jar to {os.path.abspath(store_dir)}")
    else:
        print(f"Install runnable-texturepacker.jar to {os.path.abspath(store_dir)}")


    print("Finished installing LibGDX Tools")