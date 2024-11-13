# java-explore-with-me
Ссылка для доступа к пулл реквесту:
https://github.com/lazarx77/java-explore-with-me/pull/4


Разработана функциональность приложения для поиска компаний для совместного посещения различных мероприятий в
соответствии с ТЗ второй части дипломного проекта 17 спринта.
В рамках Этапа №3 добавлена дополнительная функциональность улучшенной модерации событий администратором.
# О проекте

## Автор
Олег Лазаренко


## Описание

Этот проект представляет собой веб-приложение, которое предоставляет пять типов контроллеров для управления данными:

- **AdminController**: контроллер для администраторов, который позволяет управлять данными на уровне администратора.
- **PrivateController**: контроллер для авторизованных пользователей, который позволяет управлять данными на уровне 
- авторизованного пользователя.
- **PublicController**: контроллер для неавторизованных пользователей, который позволяет управлять данными на уровне 
- публичного доступа.
- **StatServerController**: контроллер для обработки статистических данных на сервере.

## Схемы баз данных

![main - public.png](main%20-%20public.png)![stats - public.png](stats%20-%20public.png)

## Дополнительная функциональность

- Добавлена возможность выгрузки администратором всех ожидающих модерации событий по URL
/admin/events/pending.
- При отклонении события администратором, событие переходит в статус отклененное (CANCELED) и имеется возможность
добавить комментарий администратора о причине отклонения.
- При получении подробной информации о событии его инициатором добавлена возможность просмотра комментария примины
отклонения администратором.
- В публичном доступе при просмотре подробной информации о событии комментариев причин отклонения нет.


