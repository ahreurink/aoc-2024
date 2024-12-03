set -e

mkdir day$1
cat day0/Main.java | sed s/day0/day$1/ > day$1/Main.java
