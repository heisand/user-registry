# cancer-registry

## Table of Contents
  - [Description](#description)
  - [Data model](#data-model)
  - [Installation](#installation)
    - [Backend](#backend)
    - [Frontend](#frontend)
  - [Usage](#usage)
    - [Backend](#backend-1)
    - [Frontend](#frontend-1)

## Description

This is an entirely _thought_ project for a registry system, here a cancer registry. 

It per now enables the admin part, i.e., managing users, units and their roles.

Please take a look at the [insights](https://github.com/heisand/cancer-registry/blob/main/Insights.md) about this project, explaining technical dedecisions and thoughts about future improvements.

## Data model

```mermaid
erDiagram    
    USER ||--o{ USER_ROLE : has
    UNIT ||--o{ USER_ROLE : assigned_to
    ROLE ||--o{ USER_ROLE : assigned_with
```

## Installation

```bash
# Clone the repository
git clone https://github.com/heisand/cancer-registry
```

### Backend
 - JDK

   You need to have Java Development Kit (JDK) 17 or later installed.

   Install via [Homebrew](https://brew.sh/) or other package managers, e.g., `brew install openjdk@17`.

 - Gradle

   Install via [Homebrew](https://brew.sh/) or other package managers, e.g., `brew install gradle`.

### Frontend

```bash
# Navigate to the frontend directory
cd cancer-registry/frontend

# Install dependencies
npm install
```

## Usage

### Backend

```bash
# Navigate to the backend directory
cd cancer-registry/backend

# Run
./gradlew bootRun
```

### Frontend

```bash
# Navigate to the frontend directory
cd cancer-registry/frontend

# Run
npm run dev
```
