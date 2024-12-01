set -e

javac -d output day$1/Main.java
cat day$1/input | java -cp output day$1.Main
