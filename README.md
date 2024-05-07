# Word Ladder Solver

> Tool to generate word ladders, a sequence of words to pass from one word to another by modifying only one letter at each stage.

## Table of Contents

- [General Info](#general-information)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
- [Usage](#usage)
- [Project Status](#project-status)
- [Room for Improvement](#room-for-improvement)
- [Acknowledgements](#acknowledgements)
- [Credit](#credit)

## General Information

- The Word Ladder Solver is a Java application designed to find the shortest transformation sequence between two words in a given dictionary. It employs various search algorithms, including BreaGreedy Best First Search (BFS), Uniform Cost Search (UCS), and A\*

## Technologies Used

- Java - version 11.0.6

## Features

List the ready features here:

- Algorithm selection
- Generate random words

## Setup

Make sure to set up the latest Java Environment

## Usage

1. Adjust dictionary by pasting it to the WordList.txt
2. Navigate to the src folder, open terminal and run the syntax below

`javac WordLadderSolverGUI.java GBFS.java UCS.java AStar.java`
`java WordLadderSolverGUI`

## Project Status

Project is: _complete_

## Room for Improvement

- Integration of Bidirectional BFS: Implementing Bidirectional BFS could potentially reduce search time by exploring paths from both the start and end words simultaneously.
- Dynamic Dictionary Loading: Allow users to load custom dictionaries dynamically from external files or online sources, expanding the range of words available for word ladder solutions.

## Acknowledgements

- This project was inspired by the classic word ladder problem.
- The implementation of algorithms was based on lectures and materials from the IF2211 Algorithm Strategy course at the Institut Teknologi Bandung.
- Many thanks to the teaching assistant and professors who provided guidance and support throughout the course.

## Credit

Attara Majesta Ayub
13522139
IF 2022
