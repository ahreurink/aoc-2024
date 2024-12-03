set -e

mkdir day$1
cat day0/Main.java | sed s/day0/day$1/ > day$1/Main.java
curl -H "$AOC_COOKIE" -o day$1/input https://adventofcode.com/2024/day/$1/input
