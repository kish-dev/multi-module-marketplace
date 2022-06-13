#### Обязательные задачи:

_UI и количество отображаемой информации на данном этапе не важны_

- [x] Создать android приложение, в котором:

     - реализован паттерн single activity
     - используется архитектура MVVM
     - есть 2 экрана:
         - экран со списком товаров
         - экран с детальной информацией о товаре (далее PDP)
     - архитектура разбита по слоям, согласно Clean Architecture

- [x] На экране "Список товаров" отобразить простой список из товаров типа ProductInList

- [x] На экране PDP Показываем тот продукт, на который перешли из списка. Тип Product

- [x] Для получения данных использовать mock репозиторий

- [x] Добавить счетчик количества просмотров товара (заходов на  PDP) на элементе списка

Модели данных: 
- [Product.kt](/uploads/e0bac3f83f548430fc3d2efd3b889b0a/Product.kt)
- [ProductInList.kt](/uploads/774c7016f44246448ecac5001d516b18/ProductInList.kt)

Моки: 
- ProductsInList - https://run.mocky.io/v3/50afcd4b-d81e-473e-827c-1b6cae1ea1b2
- Products - https://run.mocky.io/v3/8c374376-e94e-4c5f-aa30-a9eddb0d7d0a 

#### Дополнительные задачи:
- 💎 [Для навигации использовать Jetpack Navigation Component](https://gitlab.ozon.dev/android/classroom-2/homework-2/-/issues/2)
- 💎 [Добавление нового товара](https://gitlab.ozon.dev/android/classroom-2/homework-2/-/issues/3)
