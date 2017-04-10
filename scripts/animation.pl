#!/usr/bin/perl
use strict;
use warnings;

# merges the individual frames of an animation into a single file
# first 2 bytes - number of files
# next byte - frames per second
# for each file:
#   first 4 bytes - size in bytes
#   remaining bytes - file data
# files are merged in alphabetical order
# all numbers are big-endian

my ($dir, $out, $fps);

if (@ARGV < 3) {
    die "Usage: animation.pl <directory> <FPS> <output-file>";
} else {
    $dir = $ARGV[0];
    $fps = $ARGV[1];
    $out = $ARGV[2];
}

# open directory for reading and then remove "." and ".."
opendir DIR, $dir or die "Cannot open directory $dir: $!";
my @files = sort { lc($a) cmp lc($b) } readdir DIR;
splice(@files, 0, 2);

# open the output file for writing and write the file count and FPS
open OUT, ">", $out;
binmode OUT;
select OUT;
print pack("n", scalar @files);
print pack("C", $fps);

for (@files) {
    # open file as binary for reading
    open FILE, "$dir/$_" or die "Cannot open file $dir/$_: $!";
    binmode FILE;
    my $contents;
    read(FILE, $contents, -s "$dir/$_", 0);

    # write the file size and the contents
    print pack("N", length $contents), $contents;
    close FILE;
}

# close handles
close OUT;
closedir DIR;