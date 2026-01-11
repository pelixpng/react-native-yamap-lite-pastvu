# React Native Yandex Maps Lite (Яндекс Карты Lite)

Легковесная библиотека для интеграции Yandex Maps (Яндекс Карт) в React Native. Lite версия без маршрутов, геокодера и поиска.

## Требования

Библиотека использует **Fabric (новую архитектуру React Native)** и требует:

- **React Native >= 0.70.0** с включенной новой архитектурой

## Установка

```sh
yarn add react-native-yamap-lite
```

или

```sh
npm install react-native-yamap-lite --save
```

### iOS

После установки выполните:

```sh
cd ios && pod install && cd ..
```

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

## Использование

### Базовый пример

```jsx
import React from 'react';
import { YaMap, Marker, Circle } from 'react-native-yamap-lite';

const Map = () => {
  return (
    <YaMap
      initialRegion={{
        lat: 55.751244,
        lon: 37.618423,
        zoom: 10
      }}
      style={{ flex: 1 }}
    >
      <Marker
        point={{ lat: 55.751244, lon: 37.618423 }}
        source={require('./marker.png')}
      />
      <Circle
        center={{ lat: 55.751244, lon: 37.618423 }}
        radius={1000}
        fillColor="#0000ff"
        strokeColor="#ff0000"
        strokeWidth={2}
      />
    </YaMap>
  );
};
```

## Типы

```typescript
interface Point {
  lat: number;
  lon: number;
}

interface InitialRegion {
  lat: number;
  lon: number;
  zoom?: number;
  azimuth?: number;
  tilt?: number;
}

interface CameraPosition {
  nativeEvent: {
    zoom: number;
    tilt: number;
    azimuth: number;
    point: { lat: number; lon: number };
    finished: boolean;
    target: number;
    reason: 'GESTURES' | 'APPLICATION';
  };
}

interface MapLoaded {
  nativeEvent: {
    renderObjectCount: number;
    curZoomModelsLoaded: number;
    curZoomPlacemarksLoaded: number;
    curZoomLabelsLoaded: number;
    curZoomGeometryLoaded: number;
    tileMemoryUsage: number;
    delayedGeometryLoaded: number;
    fullyAppeared: number;
    fullyLoaded: number;
  };
}

type YandexLogoPosition = {
  horizontal?: 'left' | 'center' | 'right';
  vertical?: 'top' | 'bottom';
};

type YandexLogoPadding = {
  horizontal?: number;
  vertical?: number;
};
```

## Компонент YaMap

### Props

| Название | Тип | По умолчанию | Описание |
|--|--|--|--|
| `initialRegion` | `InitialRegion` | - | Изначальное местоположение карты при загрузке |
| `showUserPosition` | `boolean` | `false` | Отслеживание геоданных и отображение позиции пользователя |
| `followUser` | `boolean` | `false` | Слежение камеры за пользователем |
| `userLocationIcon` | `ImageSource` | - | Иконка для позиции пользователя |
| `userLocationIconScale` | `number` | `1` | Масштабирование иконки пользователя |
| `userLocationAccuracyFillColor` | `string` | `#00FF00` | Цвет фона зоны точности определения позиции пользователя |
| `userLocationAccuracyStrokeColor` | `string` | `#000000` | Цвет границы зоны точности определения позиции пользователя |
| `userLocationAccuracyStrokeWidth` | `number` | `2` | Толщина зоны точности определения позиции пользователя |
| `nightMode` | `boolean` | `false` | Использование ночного режима |
| `mapStyle` | `string` | - | Стили карты согласно [документации](https://yandex.ru/dev/maps/mapkit/doc/dg/concepts/style.html) |
| `mapType` | `'map' \| 'satellite' \| 'hybrid'` | `'map'` | Тип карты |
| `scrollGesturesEnabled` | `boolean` | `true` | Включены ли жесты скролла |
| `zoomGesturesEnabled` | `boolean` | `true` | Включены ли жесты зума |
| `tiltGesturesEnabled` | `boolean` | `true` | Включены ли жесты наклона камеры двумя пальцами |
| `rotateGesturesEnabled` | `boolean` | `true` | Включены ли жесты поворота камеры |
| `fastTapEnabled` | `boolean` | `true` | Убрана ли задержка в 300мс при клике/тапе |
| `maxFps` | `number` | `60` | Максимальная частота обновления карты |
| `logoPosition` | `YandexLogoPosition` | - | Позиция логотипа Яндекса на карте |
| `logoPadding` | `YandexLogoPadding` | - | Отступ логотипа Яндекса на карте |
| `onMapLoaded` | `(event: MapLoaded) => void` | - | Колбек на загрузку карты |
| `onCameraPositionChange` | `(event: CameraPosition) => void` | - | Колбек на изменение положения камеры |
| `onCameraPositionChangeEnd` | `(event: CameraPosition) => void` | - | Колбек при завершении изменения положения камеры |
| `onMapPress` | `(event: Point) => void` | - | Событие нажатия на карту |
| `onMapLongPress` | `(event: Point) => void` | - | Событие долгого нажатия на карту |

### Методы (через ref)

```typescript
interface YaMapRef {
  getCameraPosition: () => Promise<{
    lat: number;
    lon: number;
    zoom: number;
    azimuth: number;
    tilt: number;
  }>;
  setZoom: (
    zoom: number,
    duration?: number,
    animation?: 'LINEAR' | 'SMOOTH'
  ) => void;
  setCenter: (
    center: { lat: number; lon: number },
    zoom?: number,
    azimuth?: number,
    tilt?: number,
    duration?: number,
    animation?: 'LINEAR' | 'SMOOTH'
  ) => void;
  fitAllMarkers: () => void;
}
```

**Пример использования:**

```jsx
import React, { useRef } from 'react';
import { YaMap } from 'react-native-yamap-lite';

const Map = () => {
  const mapRef = useRef(null);

  const handleSetCenter = () => {
    mapRef.current?.setCenter(
      { lat: 55.751244, lon: 37.618423 },
      15,
      0,
      0,
      1000,
      'SMOOTH'
    );
  };

  const handleGetCameraPosition = async () => {
    const position = await mapRef.current?.getCameraPosition();
    console.log(position);
  };

  return (
    <YaMap
      ref={mapRef}
      initialRegion={{
        lat: 55.751244,
        lon: 37.618423,
        zoom: 10
      }}
      style={{ flex: 1 }}
    />
  );
};
```

## Примитивы

### Marker

```jsx
<YaMap>
  <Marker
    point={{ lat: 55.751244, lon: 37.618423 }}
    source={require('./marker.png')}
    scale={1.5}
    anchor={{ x: 0.5, y: 0.5 }}
    zInd={100}
    visible={true}
    onPress={(point) => console.log('Marker pressed', point)}
  />
</YaMap>
```

#### Props для Marker

| Название | Тип | Описание |
|--|--|--|
| `point` | `Point` | Координаты точки для отображения маркера |
| `source` | `ImageSource` | Данные для изображения маркера |
| `scale` | `number` | Масштабирование иконки маркера |
| `anchor` | `{ x: number, y: number }` | Якорь иконки маркера. Координаты принимают значения от 0 до 1 |
| `zInd` | `number` | Отображение элемента по оси Z |
| `visible` | `boolean` | Отображение маркера на карте |
| `rotated` | `boolean` | Вращение маркера при движении |
| `size` | `number` | Размер маркера в пикселях |
| `handled` | `boolean` | Включение(**false**)/отключение(**true**) всплытия события нажатия для родителя `default: true` |
| `onPress` | `(event: Point) => void` | Действие при нажатии/клике |

### Circle

```jsx
<YaMap>
  <Circle
    center={{ lat: 55.751244, lon: 37.618423 }}
    radius={1000}
    fillColor="#0000ff"
    strokeColor="#ff0000"
    strokeWidth={2}
    zInd={100}
    onPress={(point) => console.log('Circle pressed', point)}
  />
</YaMap>
```

#### Props для Circle

| Название | Тип | Описание |
|--|--|--|
| `center` | `Point` | Координаты центра круга |
| `radius` | `number` | Радиус круга в метрах |
| `fillColor` | `string` | Цвет заливки |
| `strokeColor` | `string` | Цвет границы |
| `strokeWidth` | `number` | Толщина границы |
| `zInd` | `number` | Отображение элемента по оси Z |
| `handled` | `boolean` | Включение(**false**)/отключение(**true**) всплытия события нажатия для родителя `default: true` |
| `onPress` | `(event: Point) => void` | Действие при нажатии/клике |

## ClusteredYamap

Компонент для отображения кластеризованных маркеров:

```jsx
import { ClusteredYamap, Marker } from 'react-native-yamap-lite';

<ClusteredYamap
  clusterColor="#ff00ff"
  clusteredMarkers={[
    { point: { lat: 55.751244, lon: 37.618423 }, data: {} },
    { point: { lat: 55.752244, lon: 37.619423 }, data: {} },
  ]}
  renderMarker={({ point, data }) => (
    <Marker
      point={point}
      source={{ uri: data.source }}
      size={data.size}
    />
  )}
  style={{ flex: 1 }}
/>
```

#### Props для ClusteredYamap

Все props из `YaMap` плюс:

| Название | Тип | По умолчанию | Описание |
|--|--|--|--|
| `clusteredMarkers` | `Array<{ point: Point, data: any }>` | `[]` | Массив маркеров для кластеризации |
| `renderMarker` | `(info: { point: Point, data: any }, index: number) => ReactElement` | - | Функция для рендеринга каждого маркера |
| `clusterColor` | `string` | `#FF0000` | Цвет фона метки-кластера |

## Запуск Example приложения

Перед запуском example приложения необходимо собрать нативный код для вашей платформы:

**Для iOS:**
```sh
cd example
cd ios && pod install && cd ..
yarn ios
```

**Для Android:**
```sh
cd example
yarn build:android
yarn android
```

После сборки можно запускать приложение как обычно используя `yarn ios` или `yarn android`.

**Примечание:** Все методы и примеры использования карты можно запустить и посмотреть в папке `example` проекта.

## Важные замечания

1. Компонент карт стилизуется, как и `View` из React Native. Если карта не отображается, после инициализации с валидным ключом API, вероятно необходимо прописать стиль, который опишет размеры компонента (`height + width` или `flex`).

2. Для маркеров и иконки локации пользователя можно использовать как локальные изображения (через `require('./img.png')`), так и удаленные (через `{ uri: 'https://...' }`).

3. Для отображения позиции пользователя на Android нужно запросить разрешение `android.permission.ACCESS_FINE_LOCATION`. На iOS нужно добавить `NSLocationWhenInUseUsageDescription` в `Info.plist`.

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
