
## Инициализация карт

### iOS

**Обязательно** инициализировать MapKit в функции `didFinishLaunchingWithOptions` в `AppDelegate.swift`:

```swift
import YandexMapsMobile

func application(
  _ application: UIApplication,
  didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
) -> Bool {
  // Initialize Yandex Maps BEFORE starting React Native
  YMKMapKit.setLocale("ru_RU")
  YMKMapKit.setApiKey("YOUR_API_KEY")
  YMKMapKit.initialize()
  
  // ... остальной код инициализации
  
  return true
}
```

### Android

Инициализируйте MapKit в методе `onCreate` класса `MainApplication`:

```kotlin
import com.yandex.mapkit.MapKitFactory

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    
    MapKitFactory.setLocale("ru_RU")
    MapKitFactory.setApiKey("YOUR_API_KEY")
    MapKitFactory.initialize(applicationContext)
    
    // ... остальной код инициализации
  }
}
```

**ВАЖНО:** Замените `YOUR_API_KEY` на ваш API ключ от Yandex MapKit.

## Запуск Example приложения

Перед запуском example приложения необходимо собрать нативный код для вашей платформы:

**Для iOS:**
```sh
cd ios && pod install && cd ..
yarn ios
```

**Для Android:**
```sh
yarn build:android
yarn android
```
