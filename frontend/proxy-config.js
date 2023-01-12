module.exports = [
  {
      // Single * match 1 level eg. /api/books but NOT /api/book/123
      // Double * match recursively so both
      context: [ '/**' ], // match these request
      target: 'http://localhost:8080', // SpringBoot
      secure: false, // not using HTTPS
      logLevel: 'debug'
      // ng serve --proxy-config ./proxy-config.js
  }
]
