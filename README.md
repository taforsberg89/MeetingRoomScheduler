# MeetingRoomScheduler



### INSTALLATION

Prerequisites:
  - docker

To run the demo:
  >`0. cd to this repo`\
  >`1. docker build -t meetingroom .`\
  >`2. docker run -p 8080:8080 meetingroom`
  
  
You can now hit the api at `localhost:8080/RoomScheduler/`

### USAGE

The API is very simple, it only has 3 endpoints:
- `GET /RoomScheduler/`
- `POST /RoomScheduler/{room}`
- `GET /RoomScheduler/{room}`

##### GET /RoomScheduler/

This endpoint will give you a list of rooms to book, as shown below: 

```
GET /RoomScheduler/
Response:
[
    {
        "id": 1,
        "roomType": "conferenceroom"
    },
    {
        "id": 2,
        "roomType": "coffeeroom"
    },
    {
        "id": 3,
        "roomType": "projectorroom"
    },
    {
        "id": 4,
        "roomType": "conferenceroom"
    }
]
```
##### POST /RoomScheduler/{room}

Using the list from the above step, you can book a room. Like below:
```
POST /RoomScheduler/conferenceroom
body:
{
        "startTime": "1990-05-23T05:34:24.000+0000",
        "endTime": "1992-05-23T06:34:24.000+0000",
        "roomId":1
}
```

#### GET /RoomScheduler/{room}

The last endpoint allows you to view bookings for a given room, e.g.:
```
GET /RoomScheduler/conferenceroom
Response:
[
    {
        "startTime": "1990-05-23T05:34:24.000+0000",
        "endTime": "1992-05-23T06:34:24.000+0000",
        "roomId": 1,
        "roomType": "conferenceroom"
    }
]
```
