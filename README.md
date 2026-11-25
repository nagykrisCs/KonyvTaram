# KönyvTáram – Mobil programozási alapok

**Hallgató:** Nagy Kristóf Csongor  
**Neptun-kód:** ZGEKAJ

## Leírás
Kotlin nyelvű Android-könyvnyilvántartó alkalmazás. A könyvek a készülék helyi SQLite-adatbázisában, Room könyvtárral tárolódnak.

## Funkciók
- Könyvek listázása cím szerint
- Új könyv hozzáadása (cím, szerző, műfaj, megjelenési év)
- Könyv szerkesztése a könyv sorára koppintva
- Könyv törlése megerősítéssel
- Olvasottság jelölése a jelölőnégyzettel
- Összes könyv és elolvasott könyvek számának megjelenítése
- Adatok megőrzése az alkalmazás újraindítása után

## Futtatás
1. Csomagold ki olyan mappába, amelynek teljes elérési útjában nincs ékezet (pl. `C:\AndroidProjects\KonyvTaram_ZGEKAJ`).
2. Android Studio → Open → válaszd a projekt gyökérmappáját.
3. Gradle JDK: **17**. A projekt **Gradle 7.4** és **AGP 7.3.0** verziókat céloz.
4. File → Sync Project with Gradle Files.
5. Indíts emulátort a Device Managerből, majd válaszd az `app` konfigurációt és Run.

## Fájlok
- `MainActivity.kt`: események, párbeszédablakok, listafigyelés
- `data/Book.kt`: Room entitás
- `data/BookDao.kt`: adatbázis műveletek
- `data/AppDatabase.kt`: adatbázis inicializálás
- `ui/BookAdapter.kt`: RecyclerView listaelemek
- `res/layout`: képernyő, dialógus, listaelem

## Származás és felhasználás
A projekt a felhasználó által rendelkezésre bocsátott `mobileprogramming-main` projekt átdolgozott változata. Az eredeti projekt bevásárlólista-felépítése szolgált alapul; a jelen változat könyvadatmodellt, új feliratokat és módosított felületet használ. Az eredeti szerzői és licencfeltételeket ellenőrizni kell az eredeti GitHub-repositoryban, mielőtt a munkát nyilvánosan közzéteszed vagy saját szerzőségűként adod be. A beadáskor jelezd az alapul szolgáló projektet, és győződj meg arról, hogy megfelel az oktató elvárásainak.

## Ellenőrző tesztek
1. Új könyv felvétele → megjelenik a listában
2. Könyv módosítása → a változás megjelenik
3. Elolvasva állapot bejelölése → számláló változik
4. Könyv törlése → eltűnik a listából
5. App újraindítása → a mentett adatok megmaradnak

**Megjegyzés:** A projektet a készítőnek saját Android Studio-környezetében össze kell fordítania és tesztelnie.

## Könyv szerkesztése

A könyv sorára vagy a mellette lévő ceruzaikonra koppintva megnyílik a „Könyv szerkesztése” ablak. Módosítás után a **Mentés** gomb frissíti a meglévő rekordot a Room adatbázisban.
