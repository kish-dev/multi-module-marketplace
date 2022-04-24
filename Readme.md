# Workshop Clean+MVVM+Jetpack
<ol>
## <li>Инициализация проекта - 10 мин </li>
Требование к проекту:
 - Kotlin версии 1.6.20
 - Android Gradle Plugin версии 7.1.3
 - Android Studio Android Studio Bumblebee | 2021.1.1 Patch 3

Клонируем проект с помощью команды:
git clone https://gitlab.ozon.dev/android/classroom-2/workshop-1.git

В проекте имеется папочка sources там вы найдете необходимые файлики и модельки, которые нужно будет по ходу воркшопа.

После того как проект затащили нужно будет его скомпилировать и запустить на эмуляторе.

## 2. Q&A - 5 мин

## 3. Data слой
Давайте создадим папочку data на одном уровне с папкой source. В папке data нужно будет создать две папки:
- *dto* (Для хранение объектов, которыми обмениваются бэкенд и приложение. Там могут быть и объекты для хранения в базу данных.)
- *repositoriesImpl* (Для реализации репозитория интерфейс которого будет лежать в слое domain)

Перетащите файл ProductInListDTO в папку dto из папки sources, а файл mock.kt в repositoryImpl.

## 4. Q&A - 5 мин

## 5. Domain слой
Создадим папочку domain на одном уровне с папкой *data*. В папке *domain* нужно будет создать две папки:
- *repositories* (для хранения интерфейсов repository имплементация которых лежит в слое data)
- *interactors* (для хранение интерфейса и реализации интеракторов, которые будут передавать данные из domain слоя в data слой)

Нужно будет создать интерфейс ProductsRepository с тремя методами:
> fun getProducts(): List<ProductInListVO>
> fun getProductById(guid: String): ProductVO

Теперь нужно будет сделать реализацию `ProductsRepository` c названием класса `MockProductsRepositoryImpl` в папке *data/repositoriesImpl*.
Для простоты маппинг данных будем производить в реализации самого `MockProductsRepositoryImpl`, данные для маппинга хранятся в файлике *mock.kt*.

И наконец нужно будет создать интерфейс `ProductsInteractor` с реализацией в папке *interactors*. Интерфейс должен содержать методы:
> fun getProducts(): List<ProductInListVO>
> fun getProductById(guid: String): ProductVO

## 6.

</ol>

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