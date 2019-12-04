import React from "react";
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  Platform
} from "react-native";
import * as ScrollToEhanced from "react-native-scroll-to-enhanced";

const size = 50;
const containerSize = 2000;

export default () => {
  const scrollviewIos = React.useRef();
  const scrollviewAndroidVertical = React.useRef();
  const scrollviewAndroidHorizontal = React.useRef();
  const view = React.useRef();
  const [pos, setPos] = React.useState({ top: 0, left: 0 });

  const targetElement = <View ref={view} style={[styles.target, pos]} />;

  // If you only scroll in one direction, a regular scrollview is fine - no Platform.select needed
  const multiDirectionalScrollView = Platform.select({
    ios: (
      <ScrollView
        ref={scrollviewIos}
        contentContainerStyle={styles.contentContainer}
        indicatorStyle="black"
      >
        {targetElement}
      </ScrollView>
    ),
    android: (
      <ScrollView
        ref={scrollviewAndroidVertical}
        contentContainerStyle={{ width: containerSize }}
        // Indicators get funny for multi-directional scrollviews
        showsHorizontalScrollIndicator={false}
        horizontal
      >
        <ScrollView
          ref={scrollviewAndroidHorizontal}
          contentContainerStyle={{ height: containerSize }}
          showsVerticalScrollIndicator={false}
          nestedScrollEnabled
        >
          {targetElement}
        </ScrollView>
      </ScrollView>
    )
  });

  const randomizePosition = () => {
    const top = Math.floor(Math.random() * (containerSize - size));
    const left = Math.floor(Math.random() * (containerSize - size));
    setPos({ top, left });
  };

  const scrollToRect = () => {
    const rect = { x: pos.left, y: pos.top, width: size, height: size };
    if (Platform.OS === "ios") {
      ScrollToEhanced.scrollToRect(scrollviewIos.current, rect);
    } else if (Platform.OS === "android") {
      ScrollToEhanced.scrollToRect(scrollviewAndroidVertical.current, rect);
      ScrollToEhanced.scrollToRect(scrollviewAndroidHorizontal.current, rect);
    }
  };

  const scrollToView = () => {
    if (Platform.OS === "ios") {
      ScrollToEhanced.scrollToView(scrollviewIos.current, view.current);
    } else if (Platform.OS === "android") {
      ScrollToEhanced.scrollToView(
        scrollviewAndroidVertical.current,
        view.current
      );
      ScrollToEhanced.scrollToView(
        scrollviewAndroidHorizontal.current,
        view.current
      );
    }
  };

  return (
    <>
      {multiDirectionalScrollView}
      <Button
        title={`Randomize Position (${pos.left}, ${pos.top})`}
        onPress={randomizePosition}
      />
      <Button title="Scroll via scrollToRect" onPress={scrollToRect} />
      <Button title="Scroll via scrollToView" onPress={scrollToView} />
    </>
  );
};

const Button = ({ title, onPress }) => (
  <TouchableOpacity onPress={onPress}>
    <Text style={styles.button}>{title}</Text>
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  target: {
    position: "absolute",
    backgroundColor: "red",
    width: size,
    height: size
  },
  button: {
    padding: 6,
    textAlign: "center",
    fontVariant: ["tabular-nums"]
  }
});
