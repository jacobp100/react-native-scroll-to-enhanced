export const scrollToRect: (
  scrollview: any,
  rect: {
    x: number;
    y: number;
    width: number;
    height: number;
    animated?: boolean;
  }
) => void;

export const scrollToView: (
  scrollview: any,
  view: any,
  options?: { animated?: boolean }
) => void;
