# KönyvTáram – beadandó dokumentáció (vázlat)

**Tárgy:** Mobil programozási alapok  
**Hallgató:** Nagy Kristóf Csongor  
**Neptun-kód:** ZGEKAJ

## 1. A program célja
Személyes könyvgyűjtemény vezetése Android-telefonon. A felhasználó felvihet, módosíthat, törölhet és listázhat könyveket, illetve megjelölheti az elolvasott könyveket.

## 2. Alkalmazott technológiák
Kotlin, Android SDK, Android Studio, Room (SQLite), RecyclerView, Kotlin coroutines és Flow, Material Components.

## 3. Adatmodell
**books** tábla: `id` (elsődleges kulcs), `title` (cím), `author` (szerző), `genre` (műfaj), `publicationYear` (év), `isRead` (olvasottság). A Room a `Book` adatmodellből állítja elő az adatbázis-táblát.

## 4. Funkciók
- **Létrehozás:** a + gomb megnyitja az új könyv űrlapját; a kötelező mezők ellenőrzése után Room `insert` történik.
- **Olvasás/listázás:** a DAO `getAll()` Flow-ja frissíti a RecyclerView listát.
- **Módosítás:** a listában egy könyvre koppintva szerkeszthetők az adatai; a DAO `update` menti őket.
- **Törlés:** a kukaikon megerősítés után a DAO `delete` műveletét hívja.
- **Státusz:** a jelölőnégyzettel az `isRead` mező változtatható.

## 5. Felépítés
- `MainActivity`: UI és eseménykezelés
- `BookAdapter`: lista megjelenítése és kattintáskezelés
- `Book`: Room @Entity
- `BookDao`: Room @Dao SQL-lekérdezések és CRUD
- `AppDatabase`: RoomDatabase konfiguráció

## 6. Használat és tesztelés
Nyisd meg Android Studióban, szinkronizálj JDK 17-tel, futtasd Android-emulátoron. Próbáld ki a felvitel, szerkesztés, olvasottság-jelölés, törlés funkciókat, majd indítsd újra az appot, és ellenőrizd az adatok megmaradását.

## 7. Képernyőképek
**IDE ILLYEN KÉPEKET BEILLESZTENI A SAJÁT FUTÓ PROGRAMRÓL:** kezdőképernyő, új könyv felvitele, szerkesztés, könyvlista, törlési megerősítés.

## 8. Forrás és önálló munka
Az alkalmazás a `mobileprogramming-main` eredeti példaprojekt átdolgozásából készült. A beadás előtt rögzítsd a kiinduló GitHub-repository pontos URL-jét, a megőrzendő szerzői/licencinformációkat és a saját módosításokat. A jelen dokumentáció vázlat; egészítsd ki a saját tesztelés eredményeivel.
