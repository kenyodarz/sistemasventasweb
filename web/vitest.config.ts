import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    globals: true,
  },
  esbuild: {
    target: 'es2022',
    supported: {
      'top-level-await': true
    }
  }
});
