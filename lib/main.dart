import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Login.dart';
import 'default.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await DefaultConnector.initializeFirebase(); 
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('Firestore Database')),
        body: Center(child: Text('Firebase Initialized')),
      ),
    );
  }
}