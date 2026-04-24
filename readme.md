# Instagram Follower Analyzer
[![Java](https://img.shields.io/badge/Java-17+-orange)](https://java.com)
[![Maven](https://img.shields.io/badge/Maven-3.7+-blue)](https://maven.apache.org)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)


**Backend Java CLI** | **JSON Processing** | **Follower Analytics**

Console application that analyzes Instagram JSON exports to identify:
- 🔄 Mutual followers
- ➡️ One-way followers  

## Requirements
- Java 17+
- Maven 3.7+

### 📥 How to Get Your Instagram JSON Files

1. Go to **Instagram** → **Your profile** → **Menu (≡)** → ***Your Instagram Data***
2. Select ***Request Download*** → ***JSON***
3. You will receive an **email** with a download link (0-48h)
4. Download the ZIP, extract it, navigate to **connections** → **followers_and_following**, and copy these 2 files to the project root:
   - `following.json`
   - `followers_1.json`

## Expected File Structure

These files must be in the same folder where you run the program:


- `following.json`: contains a `relationships_following` object with users you follow.
- `followers_1.json`: contains an array with users who follow you.

These files come from the Instagram data export described in the previous section.

## Compilation and Execution

```bash
mvn clean package
````

## 📁 Project Structure
```bash
src
├───main
    ├───java
        └───mgr
            └───instagram
                    JsonManager.java
                    Main.java
