import { NativeModules, findNodeHandle } from "react-native";

const { ScrollToEnhanced } = NativeModules;

export const scrollToView = (scrollview, view, { animated = true } = {}) => {
  if (scrollview == null || view == null) {
    return;
  }

  ScrollToEnhanced.scrollToView(
    findNodeHandle(scrollview),
    findNodeHandle(view),
    animated
  );
};

export const scrollToRect = (
  scrollview,
  { x, y, width, height, animated = true }
) => {
  if (scrollview == null) {
    return;
  }

  ScrollToEnhanced.scrollToRect(
    findNodeHandle(scrollview),
    { x, y, width, height },
    animated
  );
};
