# Задача 1 (Решение: script_login.sh)
Напишете Shell скрипта со која во датотеката out.txt ќе го запише вкупното време, во минути, кое одреден корисник (прв аргумент на скриптата) бил најавен. Информациите за најави се наоѓаат во датотеката logins.txt
Ако не е проследен аргументот, да се прикаже упатство за користење на скриптата. 
Доколку излезната датотека постои, потребно е да се пребрише. 
На крајот од скриптата да се прикаже содржината на датотеката out.txt.

# Задача 2 (Решение: processes.sh)
Да се напише Shell скрипта која ќе испечати колку деца процеси има секој од процесите на одреден корисник. 
Корисничкото име на корисникот се проследува како прв аргумент на скриптата. 
Ако не е проследен аргументот, да се прикаже упатство за користење на скриптата. 

# Задача 3 (Решение: copy_files.sh)
Да се напише Shell скрипта која ќе ги копира сите датотеки од именик зададен како прв аргумент кои започнуваат на број, по што следуваат само мали букви и имаат екстензија .out, во именик зададен како втор аргумент од командна линија. 
Потоа да се пресмета и да се испечати вкупната големина на ископираните датотеки за кои корисникот има привилегии за извршување. 
Доколку аргументите не се наведени, да се испише упатство за користење на скриптата, а ако не постои именикот зададен како втор аргумент, да се креира. 

# Задача 4  (Решение: rename_files.sh)
Да се напише командна процедура што ќе овозможи преименување на повеќе датотеки
одеднаш со помош на наредбата sed за пребарување и замена на текст.

# Задача 5 (Решение: grand_prix.sh)
Информации за возачки трки се чуваат во датотека race_results.tsv

Пример за запис на резултати од трка:

Lando_Norris	McLaren	2023	Monaco_Grand_Prix	2023-05-28	7	7	6 

Секој запис вклучува:
Име на возачот: Lando_Norris
Тркачки тим: McLaren
Сезона (година): 2023
Име на трката: Monaco_Grand_Prix
Датум на трката:  2023-05-28
Позиција на почеток на трката: 7
Позиција на крајот на трката: 7
Освоени поени: 6

Потребно е да напишете скрипта која ќе ги врати следните информации:

Бројот на учества на возачи на трки одржани во градот/државата која е предадена како прв аргумент на скриптата (составен дел од името на трките) за сезоната која е предадена како втор аргумент на скриптата во форма на година 
Доколку не се предадени два аргументи да се испечати порака со прикажано правилно користење: Usage: script.sh <SEASON> <LOCATION>


# Задача 6 (Решение: car_rental.sh)
Напишете скрипта која ја користи датотеката rental_sys.txt за да го пресмета вкупниот број на изнајмени луксузни возила (Luxury) во периодот од март 2024 до јули 2024.

Секој запис вклучува:
- Датум на изнајмување
- Модел на автмобил
- Категорија
- Име на изнајмувач
- Број на изнајмени возила
