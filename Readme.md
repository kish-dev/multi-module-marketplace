# Workshop Clean+MVVM+Jetpack
## 1. Создаем приложение - 15 мин
Открываем Android Studio и делаем следующие шаги:
- File -> New -> New project -> Next
- Name: Workshop1
- Language: Kotlin
- Minimum SDK: API 24 – Android 7.0
- Finish

Затаскиваем зависимости, компилируем проект и запускаем проект. Затаскиваем файл dependencies.gradle в папке sources. Прописываем
apply from: "dependencies.gradle" в build.gradle внутри buildscript {}.

## 2. Q&A - 5 мин

## 3. Добавляем package’и согласно CA - 30 мин
- Созадем структуру папок также как в примере:

  ![](sources/структура.png)

- Копируем DTO и VO объекты и создаем мапперы, чтобы привести DTO в VO объект.
- Создаем интерфейс репозитория и затаскиваем мок, который приложен в папке sources.

## 4. Q&A - 10 мин

## 5. Interactor & ServiceLocator - 15 мин
- Создаем interactor для Товара, сделать ServiceLocator с созданием зависимостей, билд проекта. Компилировать проект и запустить.

## 6. Q&A – 5 мин

## 7. Fragment & RecyclerView - 30 мин
- Создаем в activity_main.xml fragment container.
- Создаем фрагмент ProductsFragment и в классе Активити добавляем фрагмент.
- Создаем recyclerView в xml фрагмента. Разметку для item RecyclerView лежит в sources/product_list_item.xml.
- Создаем и подключаем адаптер к RV.

## 8. Q&A - 10 мин

## 9. Создаем ProductsViewModel и делаем подписку для получения данных с репозитория - 20 мин
- Создаем ProductsViewModel и затаскиваем в проект ViewModelFactory в папке sources - 5 мин
- Подписываемся на Live Data, сделать связку между Interaction + MockRepository и достать products, билд и проверка на эмуляторе - 15 мин

## 10. Q&A - 10 мин

## 11. PDPFragment - 20 мин
- Добавляем PDPFragment + UI(лежит в sources/pdp_fragment.xml) + PDPViewModel. PDPViewModel должен задержать LiveData-у, которая должа возвращать информацию о товаре
- Создаем логику перехода на PDP по клику на елемент списка товаров

## 12. Q&A - 5 мин

## 13. Доработка приложения - 20 мин
- Тестируем приложение, смотрим чтобы не было лишней загрузки данных или не сетился UI несколько раз при переходе назад и тп.

## 14. Q&A - 10 мин