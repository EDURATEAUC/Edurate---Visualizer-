library default_connector;

import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class DefaultConnector {
  static late FirebaseFirestore firestore;

 
  static Future<void> initializeFirebase() async {
    await Firebase.initializeApp();
    firestore = FirebaseFirestore.instance;
  }

  
  static FirebaseFirestore get instance {
    return firestore;
  }
}
