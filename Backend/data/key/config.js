// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAwW6pc0aQGXSA-ndqBPM3Gp8e5jqCaIB0",
  authDomain: "eng2utc.firebaseapp.com",
  databaseURL: "https://eng2utc-default-rtdb.firebaseio.com",
  projectId: "eng2utc",
  storageBucket: "eng2utc.appspot.com",
  messagingSenderId: "679823761990",
  appId: "1:679823761990:web:00cfe2a91e5d43440e6047",
  measurementId: "G-X9047FEREQ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);