# Use Case Speciﬁcation (Описание прецедента)  
  
# 1. Use-Case 1 Регистрация гражданина
*[Укажите название прецедента.]*  

Регистрация гражданина

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Гражданин, подключившийся к системе Civilla заполняет специальную форму своих личных данных для дальшейшей обработки.
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Гражданин заполняет специальную форму о себе. Фамиля, имя и любит ли он пиво.
2) Подтвердить информацию о себе.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*

1)	Гражданский присоединяется к системе Civilla. .

## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*

* Добавлен новый гражданин в систему Civilla.  

## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*
 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/fill%20my%20data.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![Fill_data)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_1.png)
![First_name)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_2.png)
![Second_name)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_3.png)
![Like_beer)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_4.png)
![Сonfirm)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_5.png)
![Data_saved)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/fill_my_data/new_step_6.png)
  
# 1. Use-Case 2 Просмотр данных гражданина
*[Укажите название прецедента.]*  

Просмотр личных данных

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Гражданин, добавив свои личные данные в систему Civilla, может просмотреть свой психопаспорт и данные профиля.
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Просмотреть данные.
2) Просмотреть свои данные.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*

1)	Гражданин должен зарегистрироваться в системе Civilla.

## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_my_data/new%20View%20my%20data.png)  
  
## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![ViewData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_my_data/step_1.png)
![MyData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_my_data/step_2.png)
![Result)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_my_data/step_3.png)
  
  # 1. Use-Case 3 Полицеский просматривает данные граждан и полицеских
*[Укажите название прецедента.]*  

Полицеский просматривает данные граждан и полицеских

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Полицейский просматривает данные всех граждан и полицейских.
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Просмотреть данные.
2) Получить данные граждан.
3) Получить данные полицейских.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*

1)	Получить роль полицеского.

## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_data_by_cop/view_data_by_cop.png) 
  
## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![ViewData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_data_by_cop/step_1.png)
![ViewCitizensCopData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_data_by_cop/step_2.png)
![DataCitizens)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_data_by_cop/step_3.png)
![DataCops)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_data_by_cop/step_4.png)  

  
# 1. Use-Case 4 Регистрация устройства
*[Укажите название прецедента.]*  

Регистрация устройства

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Полицейский регистрирует новую камеру или доминатор к системе Civilla
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Выбрать регистрацию устройства
2) Выбрать устройство
3) Заполнить специальную форму устройства

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*

* Добавлено новое устройство в систему Civilla.  

## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*
 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/register_device.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![s1)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_1.png)
![s2)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_2.png)
![s3)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_3.png)
![s4)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_4.png)
![s5)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_5.png)
![s6)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_6.png)
![s7)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_7.png)
![s8)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/Screenshot_8.png)  
  
![step1)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step1.png)
![step2)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step2.png)
![step3)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step3.png)
![step4)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step4.png)
![step5)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step5.png)
![step6)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step6.png)
![step7)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step7.png)
![step8)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/register_device/step8.png)
  
# 1. Use-Case 5 Анализ психопаспорта
*[Укажите название прецедента.]*  

Гражданин

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский, гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Посмотреть свой психопаспорт
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Просмотреть свой психопаспорт.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]* 

## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_psyhopassport/view_psyhopasport.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*  
![AnalysePsyhopassport)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_psyhopassport/Screenshot_1.png)
![MyPsyhopassport)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_psyhopassport/Screenshot_2.png)
  

# 1. Use-Case 6 Узнать о системе Civilla
*[Укажите название прецедента.]*  

Узнать о системе Civilla

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Гражданин, полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Унать больше информации о системе Civilla
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Гражданин или полицейский нажимает на кнопку About us, чтобы узнать больше информации о системе Civilla

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/about_us/about_us.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![AboutUs)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/about_us/Screenshot_2.png)
![Result)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/about_us/Screenshot_1.png)

# 1. Use-Case 7 Получить оповещение о высоком уровне психопасспорта
*[Укажите название прецедента.]*  

Получить оповещение о высоком уровне психопасспорта

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский, гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Получить оповещение о высоком уровне психопасспорта гражданина или полицейского
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Получить оповещение о повышающейся концентрации потенциальных преступников

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*

Присоединиться к системе Civilla.

## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*



## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Notification)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/notifications/notification.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![Notification)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/notifications/Screenshot_1.png)
  
# 1. Use-Case 8 Получить уведомление от устройств
*[Укажите название прецедента.]*  

Получить уведомление от устройств

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский, гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Получить уведомление о высоком уровне психопаспорта неизвестного от устройств
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Получить уведомление о превышении порогового значения коэфициента преступности у неизвестного гражданина

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Notification)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/device_notifications/notifications_device.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![Notification)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/device_notifications/Screenshot_1.png)


# 1. Use-Case 9 Редактирование своих данных
*[Укажите название прецедента.]*  

Редактирование своих данных

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский, гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Изменить свои данные в системе Civilla
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Редактировать свои данные
2) Подтвердить изменения

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*

* Изменение данных гражданина или полицейского в базе данных  

## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![EditData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/edit_data.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![EditData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_1.png)
![FirstName)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_2.png)
![SecondName)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_3.png)
![LoveBeer)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_4.png)
![Confirm)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_5.png)
![Edited)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/edit_data/step_6.png)

# 1. Use-Case 10 Панель управления полиции
*[Укажите название прецедента.]*  

Панель управления полиции

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Получить доступ ко всем функциям доступные полицескому
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Получить панель управления полицейского

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![EditData)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/police_control_panel/police_control_panel.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![ControlPanel)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/police_control_panel/Screenshot_1.png)

# 1. Use-Case 11 Просмотр устройств
*[Укажите название прецедента.]*  

Просмотр устройств

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Просмотреть список зарегестрированных камер и доминаторов
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Просмотреть устройства
2) Просмотреть список камер
3) Просмотреть список доминаторов

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![ViewDevices)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_devices/view_devices.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![ViewDevices)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_devices/Screenshot_1.png)
![ChooseDevice)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_devices/Screenshot_2.png)
![Cameras)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_devices/Screenshot_3.png)
![Dominators)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/view_devices/Screenshot_4.png)

 # 1. Use-Case 12 Доступ к камере
*[Укажите название прецедента.]*  

Доступ к камере

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Получить доступ к камере
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Запросить доступ на использование конкретной камеры.
2) Получить резрешение или отказ на использование камеры.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_acces_cameras/access_cameras_1.png) 
  
## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![Error)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_acces_cameras/Screenshot_1.png)
![Ok)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_acces_cameras/Screenshot_2.png)
 
  
  # 1. Use-Case 13 Контроль доступа доминатору
*[Укажите название прецедента.]*  

Контроль доступа доминатору

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Полицейский получает доступ к доминатору для его использования.
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Запросить доступ на использование конкретного доминатора.
2) Получить резрешение или отказ на использование оружия доминатора.

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*

1)	Получить роль полицеского.

## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*

 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_access/request%20access_1.png) 
  
## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![Error)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_access/access_1.png)
![Ok)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/request_access/access_2.png) 

# 1. Use-Case 14 На предыдущее окно
*[Укажите название прецедента.]*  

На предыдущее окно

## 2. Actors (Акторы)
*[Укажите основное действующее лицо прецедента и дополнительных (если они есть)]*

Полицейский, гражданин
  
### 2.1 Brief Description (Краткое описание)
*[Кратко опишите прецедент, с чего он начинается.]*
  
Перейти на предыдущее окно
  
## 3. Flow of Events (Последовательность событий)

### 3.1 Basic Flow (Главная последовательность)
*[Опишите последовательность шагов, которые выполняет пользователь. Описание должно быть максимально абстрактным, без привязки к элементам интерфейса и вводу определенных данных.]*  

1) Нажать на кнопку back

## 4. Preconditions (Предусловия)
*[Укажите условия, которые должны выполняться, чтобы прецедент начался.]*


## 5. Postconditions (Постусловия)  
*[Укажите, что изменится в системе после выполнения прецедента.]*


## 6. Extension Points (Точки расширения)
*[Если прецедент содержит вложенные прецеденты, которые добавляют к нему новые шаги, укажите здесь ссылку на них, на каком шаге главной последовательности они включаются.]*
 
## 7. Use-case diagram (Диаграмма прецедента)
*<UML диаграмма данного прецедента>* 
![Диаграмма прецедента)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/button_back/button_back.png)  

## 8. Interface example (Пример интерефейса)
*<Макет пользовательского интерфейса, который будет использоваться для выполнения прецедента>*
![ButtonBack)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/button_back/Screenshot_1.png)
![PreviousWindow)](https://github.com/FF220v/ITMO-Psychopass-Team/blob/al_use_cases_new/docs/pics/button_back/Screenshot_2.png)
