# Завдання до 1 модулю

Для того, щоб запустити код достатньо виконати наступну команду:

``github.com``

# Додатково:

Для завдання обрав тему музики(пісня-виконавець). Приклад:

```json
[
  {
    "title": "What I've Done",
    "duration": 3.28,
    "release": 2007,
    "genre": "alternative rock, nu-metal",
    "artist": "Linkin Park"
  }
]
```

# Пояснення до програми:

```java
public class Song {
    private String title;
    private Float duration;
    private String album;
    private String genre;
    private String artist;
}
```
Враховуючи, що це 1 завдання артиста описано за допомогою String. В подальшому це буде окрема сутність.
1) ### Json

### Користувач запускає додаток через головний Main.java клас.

Є можливість отримати статистику по всій директорії (В цьому випадку ми отримаємо List<Map<K, V>>).
Після виконання методу mergeFromList(List<Map<K, V>>) отримаємо Map<K, V>. Логіка ця виконується для T extends Number та String.
В програмі перевірив на String, Float та String, яке містить значення через кому.

### Варто додати, що JsonParser містить ExecutorService і можна задавати кількість потоків (В мене 6 файлів => 2,4,6 потоків)
```
Total execution time: 0.0915303 seconds -- 2 потоки
Total execution time: 0.0902726 seconds -- 4 потоки
Total execution time: 0.0876881 seconds -- 6 потоки
```

2) ### XML

Отримавши статистику вигляду Map<K, V> користувач може завантажити дані в .xml файл вигляду:

```xml

<Statistics>
    <items>
        <items>
            <value>The End Is Where We Begin</value>
            <count>12</count>
        </items>
        <items>
            <value>Order in Decline</value>
            <count>7</count>
        </items>
        ...
    </items>
<Statistics>
```

Для більшої зручності додав сутності для роботи з Xml:
```java
public class Item<T> {
    private T value;
    private Integer count;
}
```

```java
public final class Statistics<T> {
    private List<Item<T>> items;
}
```