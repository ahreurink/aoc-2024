set -e

javac -d output day$1/Main.java

if [ "$3" = "ex" ]; then
  cat day$1/example_input | java -cp output day$1.Main $2
else
  echo input
  cat day$1/input | java -cp output day$1.Main $2
fi
