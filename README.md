![title](https://github.com/smmin21/ACTemo-server/assets/79392773/4e7424d2-4f99-46c8-a0eb-982f530e9a8e)


<details>
<summary>Table of Contents</summary>

- [🔮 Features](#-features)
- [📜 API Endpoints](#-api-endpoints)
    - [`1` Emotion Information API](#1-emotion-information-api)
    - [`2` Script Information API](#2-script-information-api)
- [🔗 Link to Other Parts](#-link-to-other-parts)

</details>


---

## 🔮 Features
Our **ACTemo**'s API Server is designed to provide emotion information and emotion script data through a convenient API. It's built using Java Spring and deployed on Google Compute Engine.

<br>

## 📜 API Endpoints
Below, you'll find details on how to interact with these APIs to retrieve emotion and script information.

### **`1` Emotion Information API**
#### 1-1. Get Emotion Information

| ID     | URL               | METHOD |
| ------ | ----------------- | ------ |
| BA01-1 | /script/{emotion} | GET    |

This API retrieves emotion information for a specific emotion. Here, `emotion` can be one of the following:
```
'Angry', 'Distressed', 'Content', 'Happy', 'Excited', 'Joyous', 'Depressed', 'Sad', 'Bored', 'Calm', 'Relaxed', 'Sleepy', 'Afraid', 'Surprised'
```

##### Response
The JSON response includes the following fields:

- `id`: Unique identifier for the emotion
- `emotion`: The emotion itself
- `emoji`: Emoji representing the emotion
- `category`: Category of the emotion
- `imagePath`: Path to the image representing the emotion
- `definition`: Definition of the emotion
- `example`: Example illustrating the emotion


### **`2` Script Information API**
> [!NOTE]
> Don't forget to include the user token or ID in the Authorization header for authentication purposes!
#### 2-1. Get Script Information

| ID     | URL              | METHOD |
| ------ | ---------------- | ------ |
| BA02-1 | /scene/{emotion} | GET    |

This API retrieves script information for a specific emotion. Here, `emotion` can be one of the following:
```
'Angry', 'Distressed', 'Content', 'Happy', 'Excited', 'Joyous', 'Depressed', 'Sad', 'Bored', 'Calm', 'Relaxed', 'Sleepy', 'Afraid', 'Surprised'
```

##### Response
The JSON response includes the following fields:

- `id`: Unique identifier for the script
- `emotion`: Emotion associated with the script
- `name`: Name of the script
- `situation`: Situation of the script
- `dialogue`: List of dialogues, where each dialogue contains the following keys:
    - `name`: Name associated with the dialogue
    - `message`: Message of the dialogue

#### 2-2. Mark Script Practice as Completed

| ID     | URL                    | METHOD |
| ------ | ---------------------- | ------ |
| BA02-2 | /user-scene/{scene_id} | POST   |

This endpoint marks a script practice as completed for a specific Scene id.

##### Response
- “success”


<br>

## 🔗 Link to Other Parts

If you want to explore other parts of the project, feel free to navigate to:

- [Overall Project Repository](https://github.com/smmin21/ACTemo-Google-Solution-Challenge-2024): For the complete project repository, including all parts, visit the GitHub repository.
- [Frontend Development](https://github.com/e6d1fe/ACTemo-flutter.git): Explore the frontend development aspects of the project, including UI design and user interaction, implemented using Flutter.
- [Machine Learning Server Development](https://github.com/smmin21/ACTemo-server): Explore the model development aspects of the project, including instructions on how to run the server using Docker and making your very own request to our API server.

