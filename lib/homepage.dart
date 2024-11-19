import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'styles.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Home', style: headingStyle.copyWith(color: Colors.white)),
      ),
      body: StreamBuilder(
        stream: FirebaseFirestore.instance.collection('courses').snapshots(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) return CircularProgressIndicator();
          final courses = snapshot.data!.docs;
          return ListView.builder(
            itemCount: courses.length,
            itemBuilder: (context, index) {
              return ListTile(title: Text(courses[index]['name']));
            },
          );
        },
      ),
    );
  }
}
