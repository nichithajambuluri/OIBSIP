# RailReserve — Oasis Infobyte Java Task 1 (V3.2)

# RailReserve — OIBSIP Java Task 1

A polished Java Swing + SQLite railway reservation and train information application for the Oasis Infobyte Java Development Internship.

## What this version includes

### Oasis Task 1 requirements
- Login with username/password validation
- Reservation form with passenger name, train number, automatic train details, class, journey date, source and destination
- Unique 10-digit PNR generation
- SQLite persistence through JDBC
- Find booking by PNR
- Confirmation before cancellation
- Required-field, train-number and future-date validation
- 25 seeded demo trains
- Saved Bookings / booking history

### V3 experience upgrades
- Modern FlatLaf Swing interface
- Professional sidebar navigation and dashboard
- Clean cards, spacing, typography and rounded controls
- Train Information API integration
- Train Schedule API integration
- Train Fare API integration
- Live Train Status API integration
- Station autocomplete API integration
- Station Name → Code API support
- Station Code → Name API support
- Station Location on Map support
- API calls run in SwingWorker so the UI does not freeze while waiting for the network
- API failures fall back cleanly to local demo data where appropriate
- API key is kept outside source code and ignored by Git

## Technology
- Java 17+
- Java Swing
- FlatLaf
- JDBC
- SQLite
- Gson
- Maven
- Indian Rail API (optional external integration)

## Project structure

```text
OIBSIP/
└── Java-Task1-OnlineReservationSystem/
    ├── config/
    │   └── api.properties.example
    ├── database/
    │   └── schema.sql
    ├── screenshots/
    ├── src/main/java/com/oasis/reservation/
    │   ├── api/
    │   ├── dao/
    │   ├── database/
    │   ├── model/
    │   ├── service/
    │   ├── ui/
    │   │   └── api/
    │   └── util/
    ├── .gitignore
    ├── pom.xml
    └── README.md
```

## Run in IntelliJ IDEA

1. Extract the project.
2. Open the folder containing `pom.xml` in IntelliJ IDEA.
3. Select JDK 17 or newer.
4. Let IntelliJ import the Maven project and download dependencies.
5. Run `src/main/java/com/oasis/reservation/Main.java`.
6. Demo login: `admin` / `admin123`.

## Optional Indian Rail API setup

1. Copy `config/api.properties.example` to `config/api.properties`.
2. Put your API key in `api.key`.
3. Change `api.enabled=false` to `api.enabled=true`.
4. Do not commit `config/api.properties` to GitHub. It is already listed in `.gitignore`.

The application uses these supplied endpoints:

- `TrainInformation`
- `TrainSchedule`
- `TrainFare`
- `AutoCompleteStation`
- `StationNameToCode`
- `StationCodeToName`
- `StationLocationOnMap`
- `livetrainstatus`

The application does **not** claim to be an IRCTC booking client. Local SQLite reservations are the internship application's own demo reservation records.

## Recommended demo flow

1. Sign in.
2. Show the dashboard and saved-booking count.
3. Open Book Ticket.
4. Enter a sample train number such as `12723` and demonstrate automatic train information.
5. Enter a future date and passenger name, then book.
6. Show the generated PNR confirmation.
7. Open My Bookings and show persistence.
8. Open Find / Cancel and retrieve the PNR.
9. Demonstrate the confirmation step before cancellation.
10. Open Rail Information and show Train Details, Schedule, Fare and Live Status.
11. If the API key is configured, demonstrate station autocomplete and live API data.

## Important

The API integration is an optional enhancement. The core Oasis reservation workflow remains functional using the local SQLite database and seeded train catalogue when the external API is unavailable.


## V3.2 UI fixes
- Fixed railway information tabs so their input controls and result areas remain visible.
- Added working station Name→Code and Code→Name actions.
- Added clearer API-disabled messaging and compact result panels.
- Train selector remains catalogue-backed so booking can be demonstrated without an API key.
