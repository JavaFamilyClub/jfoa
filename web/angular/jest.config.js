module.exports = {
   globals: {
      'ts-jest': {
         tsConfig: './tsconfig.spec.json'
      }
   },
   setupFiles: ["jest-canvas-mock", "<rootDir>/src/setupJest.ts"],
   coverageDirectory: "<rootDir>/build/coverage/",
   collectCoverage: true
};
