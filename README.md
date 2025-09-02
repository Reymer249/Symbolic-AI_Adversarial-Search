# Symbolic AI – Adversarial Search in Blocker Game

This repository contains a project on **Adversarial Search** completed as part of the Symbolic AI course at Leiden University (2023).  
We implement **Minimax** and **Alpha-Beta pruning** algorithms to play a two-player competitive game called **Blocker**, where agents compete to collect food on a grid-based maze.

---

## 📖 Project Overview
- **Game environment**: 
  - Grid-based maze read from text files (`board.txt`)
  - Agents have 6 actions: {up, right, down, left, eat, block}
  - Objective: collect more food than the opponent or leave them with no legal moves
  - Fully observable environment with fixed starting positions for agents
- **Algorithms implemented**:
  - **Minimax**: Depth-limited recursive adversarial search
  - **Alpha-Beta pruning**: Minimax with branch pruning to reduce search nodes
- **Key challenges**:
  - Exponential growth of search tree with depth and branching factor
  - Handling ties, wins, losses, and illegal moves
  - Preventing infinite loops via shuffling move order
- **Experiments**:
  - Analyzed outcomes at varying search depths (6–14)
  - Compared number of visited nodes for Minimax vs. Alpha-Beta pruning
  - Observed agent behavior, strategy, and decision-making efficiency

---

## 🔑 Key Findings
- **Alpha-Beta pruning** drastically reduces search nodes compared to Minimax (up to 116× fewer nodes at depth 13)
- **Search depth**: Deeper searches lead to more optimal agent decisions, but can introduce longer games in certain board configurations
- **Shuffling moves** prevents infinite loops caused by deterministic move order
- Both algorithms exhibit **exponential complexity**, consistent with theoretical expectations
- **State representation**:
  - `State` class stores board, agent positions, scores, turn, and executed moves
  - Copying via serialization ensures independent states for recursive search

---

## 📂 Repository Contents
- `Report.pdf` – Full report for the assignment
- `Assignment.pdf` - An assignment statement specifying the tasks to complete
- `src/adversarialsearch/`
  - `Game.java` – Implementation of Minimax and Alpha-Beta pruning  
  - `State.java` – Board representation and game logic, including move execution and evaluation
  - `Main.java` – Entry point for running the game
- `data/`
  - `board.txt` - Example of the game board
- `README.md` - README file (this file)

---

## 🚀 Getting Started
Clone the repository and open the project in **Eclipse**:

```bash
git clone https://github.com/Reymer249/Adversarial_search.git
