@echo off

javac -cp "jars/*;.;resources/*" implementation/Knapsack.java implementation/BinaryTree.java
java -cp ".;jars/*" implementation.BinaryTree