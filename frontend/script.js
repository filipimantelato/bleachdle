let characters = [];
let selectedIndex = -1;
let currentResults = [];
let gameOver = false;
let attempts = 0;
let guessedCharacters = [];
let countdownInterval = null;

const resultSection = document.querySelector(".result-section");
const winnerImage = document.querySelector(".winner-image");
const winnerName = document.querySelector(".winner-name");
const winnerAttempts = document.querySelector(".winner-attempts");
const countdown = document.querySelector(".countdown");
const searchInput = document.querySelector(".search-input");
const searchResults = document.querySelector(".search-results");
const tableBody = document.querySelector(".game-table tbody");
const gameTable = document.querySelector(".game-table");
const streakCount = document.getElementById("streak-count");
const streakIcon = document.querySelector(".streak-icon");
const helpButton = document.getElementById("helpButton");
const helpModal = document.getElementById("helpModal");
const closeModal = document.getElementById("closeModal");

helpButton.addEventListener("click", () => {
    helpModal.classList.remove("hidden");
});

closeModal.addEventListener("click", () => {
    helpModal.classList.add("hidden");
});

helpModal.addEventListener("click", (event) => {

    if(event.target === helpModal){
        helpModal.classList.add("hidden");
    }

});

document.addEventListener("keydown", (event) => {

    if(event.key === "Escape"){
        helpModal.classList.add("hidden");
    }

});

async function loadCharacters() {
    const response = await fetch(
        "http://localhost:8080/characters/search"
    );
    characters = await response.json();

    await checkGameState();
    checkStreakReset();
    updateStreakUI();

    const progress = await loadProgress();

    console.log(progress);

    if(progress && progress.solved){

        gameOver = true;

        searchInput.disabled = true;

        searchInput.placeholder =
            "You already solved today's Bleachdle!";

        showVictory(
            progress.characterName,
            progress.characterImage,
            progress.attempts
        );

        return;
    }
}

async function checkGameState(){

    const response = await fetch(
        "http://localhost:8080/characters/game-state"
    );

    const data = await response.json();

    console.log(data);

    if(data.solved){

        gameOver = true;

        searchInput.disabled = true;

        searchInput.placeholder =
            "You already solved today's Bleachdle!";

    }
}

function hashString(text){
    let hash = 0;

    for(let i = 0; i < text.length; i++){

        hash =
            ((hash << 5) - hash)
            + text.charCodeAt(i);

        hash |= 0;
    }

    return Math.abs(hash);
}

function getPlayerId(){

    let playerId =
        localStorage.getItem("bleachdle-player-id");

    if(!playerId){

        playerId = crypto.randomUUID();

        localStorage.setItem(
            "bleachdle-player-id",
            playerId
        );
    }

    return playerId;
}

function getStorageKey(){
    const today = new Date();

    return "bleachdle-" +
        today.toISOString().slice(0,10);
}

function saveGame(){
    const data = {
        date: new Date().toISOString().slice(0,10),
        attempts: attempts,
        gameOver: gameOver,
        guesses: [...guessedCharacters]
    };

    localStorage.setItem(
        getStorageKey(),
        JSON.stringify(data)
    );
}

// function loadGame(){
//     const save = localStorage.getItem(
//         getStorageKey()
//     );

//     if(!save){
//         return;
//     }

//     const data = JSON.parse(save);

//     attempts = data.attempts;
//     gameOver = data.gameOver;

//     guessedCharacters = data.guesses || [];

//     guessedCharacters.forEach(id=>{

//         const character =
//             characters.find(c => c.id === id);

//         if(character){
//             addGuess(character, true);
//         }

//     });

//     if(gameOver){
//         searchInput.disabled = true;
//         searchInput.placeholder = "You already solved today's Bleachdle!";
//         showVictory();
//     }
// }

function updateStreakUI() {
    const streak = Number(localStorage.getItem("bleachdle-streak")) || 0;

    streakCount.textContent = streak;

    streakIcon.classList.remove("glow", "epic", "legend");

    if (streak >= 5 && streak < 15) {
        streakIcon.classList.add("glow");
    }

    if (streak >= 15 && streak < 30) {
        streakIcon.classList.add("epic");
    }

    if (streak >= 30) {
        streakIcon.classList.add("legend");
    }
}

function increaseStreak() {
    const today = new Date().toISOString().slice(0, 10);
    const yesterday = new Date(Date.now() - 86400000)
        .toISOString()
        .slice(0, 10);

    const lastWin = localStorage.getItem("bleachdle-last-win");
    let streak = Number(localStorage.getItem("bleachdle-streak")) || 0;

    if (lastWin === today) {
        return;
    }

    if (lastWin === yesterday) {
        streak++;
    } else {
        streak = 1;
    }

    localStorage.setItem("bleachdle-streak", streak);
    localStorage.setItem("bleachdle-last-win", today);

    updateStreakUI();
}

function checkStreakReset() {
    const lastWin = localStorage.getItem("bleachdle-last-win");

    if (!lastWin) {
        return;
    }

    const today = new Date().toISOString().slice(0, 10);

    const diff = daysBetween(lastWin, today);

    if (diff > 1) {
        localStorage.setItem("bleachdle-streak", 0);
    }
}

function daysBetween(date1, date2) {
    const d1 = new Date(date1);
    const d2 = new Date(date2);

    d1.setHours(0,0,0,0);
    d2.setHours(0,0,0,0);

    return Math.floor(
        (d2 - d1) / (1000 * 60 * 60 * 24)
    );
}

function handleSearch(event){

    if(gameOver){
        return;
    }

    const searchText = event.target.value.trim();

    clearResults();

    if(searchText.length < 1){
        return;
    }

    currentResults = characters.filter((character) => {
        const alreadyGuessed =
            guessedCharacters.includes(character.id);

        return (
            character.name
                .toLowerCase()
                .includes(searchText.toLowerCase())
            &&
            !alreadyGuessed
        );
    });

    selectedIndex = -1;
    showResults(currentResults);
}

function clearResults(){
    searchResults.innerHTML = "";
}

function addGuess(result, restore = false){
    if(!restore){
        attempts++;
    }

    gameTable.classList.add("show-header");

    const columns = [
        "guessedName",
        "guessedGender",
        "guessedAgeGroup",
        "guessedRace",
        "heightValue",
        "guessedHairColor",
        "guessedFirstLocation",
        "guessedStatus"
    ];

    const row = document.createElement("tr");

    columns.forEach((column) => {

        const cell = document.createElement("td");

        if(column === "guessedName"){
            cell.innerHTML = `
                <div class="character-name">
                    <img src="${result.image}" class="character-icon">
                    <span>${result.guessedName}</span>
                </div>
            `;
        }
        else{
            cell.innerHTML = formatCellValue(
                column,
                result[column]
            );
        }

        let cellClass;

        switch(column){

            case "guessedName":
                cellClass = result.name;
                break;

            case "guessedGender":
                cellClass = result.gender;
                break;

            case "guessedAgeGroup":
                cellClass = result.ageGroup;
                break;

            case "guessedRace":
                cellClass = result.race;
                break;

            case "heightValue":
                if(result.height === "correct"){
                    cellClass = "correct";
                }
                else{
                    cellClass = "wrong";
                }

                break;

            case "guessedHairColor":
                cellClass = result.hairColor;
                break;

            case "guessedFirstLocation":
                cellClass = result.firstLocation;
                break;

            case "guessedStatus":
                cellClass = result.status;
                break;
        }

        cell.classList.add(cellClass);

        if(column === "heightValue"){
            if(result.height === "up"){
                cell.innerHTML += "<br>⬆";
            }

            if(result.height === "down"){
                cell.innerHTML += "<br>⬇";
            }
        }

        row.appendChild(cell);

    });

    tableBody.appendChild(row);

}


async function selectCharacter(character){

    if(gameOver){
        return;
    }

    guessedCharacters.push(character.id);

    searchInput.value = "";
    clearResults();

    selectedIndex = -1;
    currentResults = [];

    const result = await sendGuess(character.name);

    console.log(result);

    addGuess(result);

    searchInput.focus();

    checkWin(result);

    saveGame();
}

function showResults(filteredCharacters){

    clearResults();

    filteredCharacters.forEach((character)=>{

        const div = document.createElement("div");

        div.classList.add("search-item");

        div.innerHTML = `
            <img src="${character.image}" class="search-image">
            <span>${character.name}</span>
        `;

        div.addEventListener("click", () => {
            selectCharacter(character);
        });

        searchResults.appendChild(div);

    });

}

function updateSelection(){

    const items = document.querySelectorAll(".search-item");

    items.forEach((item,index)=>{

        item.classList.toggle(
            "active",
            index === selectedIndex
        );

    });
}

function handleKeyDown(event){
    if(gameOver){
        return;
    }

    if(event.key === "ArrowDown"){

        if(selectedIndex < currentResults.length - 1){
            selectedIndex++;
        }
    }

    if(event.key === "ArrowUp"){

        if(selectedIndex > 0){
            selectedIndex--;
        }
    }

    if(event.key === "Enter"){
        if(currentResults.length === 1){
            selectCharacter(currentResults[0]);
            return;
        }

        if(selectedIndex >= 0){
            selectCharacter(currentResults[selectedIndex]);
        }
    }

    updateSelection();
}

function formatCellValue(column, value, character){
    if(column === "name"){
        return `
            <div class="character-name">
                <img src="${character.image}" class="character-icon">
                <span>${character.name}</span>
            </div>
        `;
    }

    if(Array.isArray(value)){
        return value.join("<br>");
    }

    return value;
}

function showVictory(characterName, characterImage,attemptsValue = attempts){
    resultSection.classList.remove("hidden");

    winnerImage.src = characterImage;
    winnerName.textContent = characterName;

    winnerAttempts.textContent =
    `Attempts: ${attemptsValue}`;

    updateCountdown();

    if(!countdownInterval){
        countdownInterval =
            setInterval(updateCountdown, 1000);
    }

    resultSection.scrollIntoView({
        behavior:"smooth"
    });
}

function updateCountdown(){
    const now = new Date();
    const tomorrow = new Date();
    tomorrow.setHours(24,0,0,0);
    const diff = tomorrow - now;
    const hours = Math.floor(diff / 1000 / 60 / 60);
    const minutes = Math.floor(diff / 1000 / 60 % 60);
    const seconds = Math.floor(diff / 1000 % 60);
    countdown.textContent =
        `${String(hours).padStart(2,"0")}:${String(minutes).padStart(2,"0")}:${String(seconds).padStart(2,"0")}`;
}

function checkWin(result){
    if(result.name === "correct"){

        gameOver = true;

        increaseStreak();

        showVictory(
            result.guessedName,
            result.image,
            attempts
        );

        saveGame();
    }
}

async function loadProgress() {

    const response = await fetch(
        `http://localhost:8080/characters/progress/${getPlayerId()}`
    );

    const text = await response.text();

    if (!text) {
        return null;
    }

    return JSON.parse(text);
}

async function sendGuess(characterName){

    console.log({
        name: characterName,
        playerId: getPlayerId()
    });

    const response = await fetch(
        "http://localhost:8080/characters/guess",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: characterName,
                playerId: getPlayerId(),
                attempts: attempts + 1
            })
        }
    );

    return await response.json();
}

loadCharacters();
searchInput.addEventListener("input", handleSearch);
searchInput.addEventListener("keydown", handleKeyDown);