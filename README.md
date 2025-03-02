# **BTree Toolkit** 🌳
Hello world! This Java project consists in a implementation of a simple optimized custom **BTree** data structure with a custom **QuickSort** algorithm with it. This repository includes a command-line client that allows you to interactively manage and explore the tree, along with a custom sorting utility to allow you to easily play with what we made and maybe use in your personal project.

Before anything note that this work was done by two university students so don't take it too seriously because in reality we are just having fun doing something coding related. Feel free to make any comments in relation to code improvements and feel free to use it as you please

---

## **Table of Contents** 📑

- [Features](#features)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [License](#license)
- [Acknowledgments] (#Acknowledgments)

## **Features** 🚀

- **Interactive Command-Line Interface**:  
  Engage with the tree through an easy-to-use CLI.

- **Dynamic Tree Operations**:
  - **Insert**: Add key-value pairs dynamically.
  - **Get**: Retrieve values by key.
  - **Contains**: Check if a key exists.
  - **Size & Height**: Get insights into the number of elements and tree depth.
  - **Min & Max**: Quickly find the smallest and largest keys.
  - **Range Queries**: List keys/values within a specified range.
  - **Rank & Select**: Determine the rank of a key or select a key by its order.
  - **Floor & Ceiling**: Find the closest keys that are less than or greater than a given key.

---

## **Project Structure** 🗂️
**BTrees-QSort**
- **src/**
  - **FTreeClient.java** – CLI client for interacting with the FTree
  - **FTree.java** – Custom tree data structure implementation
  - **QuickSort.java** – Custom QuickSort algorithm implementation
- **README.md** – This file – your guide to the project
- **LICENSE** – Project license (MIT)

---

## **Installation & Setup** 🔧

### **Prerequisites** ✅

- **Java 8** or later installed on your machine.
- **Git** for cloning the repository.

### **Steps to Install** 🛠️

1. **Clone the repository**:
    ```bash
    git clone https://github.com/PantocaPipoca/Java-Btree.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd Java-Btree
    ```
3. **Compile the project**:
    ```bash
    mkdir bin
    ```

4. **Compile the project**:
    ```bash
    javac -d bin *.java
    ```

5. **Run the FTree Client**:
    ```bash
    java -cp bin FTreeClient
    ```

---

## **License** 📜

This project is licensed under the **MIT License**. For more details, see the [LICENSE](LICENSE) file.

---

## **Acknowledgments** 🙏

This project was done by two university students Daniel Pantyukhov and Valentim Khakhitva

---

Feel free to reach out with any questions or suggestions.
