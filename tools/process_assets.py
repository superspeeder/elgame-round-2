#!/usr/bin/env python

import sys, os

def try_locate_texpacker_jar(self):
    dirs_to_check = ['./','bin/','jars/']
    if sys.platform == 'linux':
        dirs_to_check = ['/usr/bin/', 'usr/local/bin', '~/.gdxtools/bin']

def build_atlas(self, cfg, texpacker_jar):
    pass

PROCESSABLE_ASSET_TYPES = [
    "atlas"
]



if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Process Assets")
    parser.add_argument('-A','--atlas', action='append', nargs=1
