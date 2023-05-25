const express = require('express');
const multer = require('multer');

const app = express();
const upload = multer({ dest: 'uploads/' });

app.post('/upload', upload.single('uceniciDatabase'), (req, res) => {
  // Handle the uploaded file
  const file = req.file;
  // Process the file as needed
  res.send('File uploaded successfully');
});

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});