# Deprecated

## You're recommended to migrate to [react-native-scrollintoview](https://github.com/jacobp100/react-native-scrollintoview), which supports Fabric

# react-native-scroll-to-enhanced

Some extra scrolling methods for scroll views

```
yarn add react-native-scroll-to-enhanced
```

Uses native code. React Native 0.61+ will handle this automatically. For older versions, follow whatever other packages do.

```js
import React from "react";
import { View, ScrollView } from "react-native";
import { scrollToRect, scrollToView } from "react-native-scroll-to-enhanced";

const Example = () => {
  const scrollview = React.useRef();
  const view = React.useRef();

  const scrollToRectExample = () => {
    // Scroll to an exact rectangle
    // No-ops if the rectangle is already fully visible on screen
    scrollToRect(scrollview.current, {
      x: 50,
      y: 100,
      width: 200,
      height: 200
    });
  };

  const scrollToViewExample = () => {
    // Scroll to any child view
    // Does not need to be a direct descendant
    // No-ops if the child view is already fully visible on screen
    scrollToView(scrollview.current, view.current);
  };

  return (
    <ScrollView ref={scrollview}>
      <View ref={view} />
    </ScrollView>
  );
};
```

```js
type scrollToRect = (
  scrollview: any,
  rect: {
    x: number,
    y: number,
    width: number,
    height: number,
    animated?: boolean
  }
) => void;

type scrollToView = (
  scrollview: any,
  view: any,
  rect?: { animated?: boolean }
) => void;
```

For a more complex example, including multi-directional scrolling, see the example
