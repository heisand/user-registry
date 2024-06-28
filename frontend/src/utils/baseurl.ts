import { isDevEnvironment } from "./environment";

export const BASE_URL = isDevEnvironment()
  ? "http://localhost:8080"
  : window.location.origin;

export const PRODUCTION_URL = `https://www.cancer-registry`;
