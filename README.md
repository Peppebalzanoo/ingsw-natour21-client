# NaTour21 🇮🇹🌍🏞️

**NaTour21** is a complex and distributed system developed during the Software Engineering course. It aims to offer a modern, cross-platform social network for hiking enthusiasts. The system features a secure, scalable back-end and an intuitive mobile client, providing users with a fast and enjoyable experience. 
NaTour21 is a comprehensive distributed system designed and developed during the Software Engineering course as part of a university project. The main objective of NaTour21 is to create a modern social network for hiking enthusiasts, offering a platform where users can share, discover, and discuss various hiking trails (itineraries), natural points of interest, and much more.

This cross-platform system is composed of a secure, scalable back-end powered by modern cloud technologies and a mobile client that allows users to interact with the system seamlessly. Whether you're a casual hiker or an experienced trekker, NaTour21 enhances the outdoor adventure experience by providing a social space where information on trails, points of interest, and user recommendations are easily accessible.
## 🚀 Main Features

1. **User Registration/Authentication** 🔑  
   Users can register and log in, with support for third-party authentication (Google, Facebook).

2. **Trail Management** 🥾  
   Authenticated users can add new hiking trails. Each trail includes:
   - Name, duration, difficulty level, starting point
   - Optional description and geographic track, manually or via GPX file.

3. **Points of Interest (POIs)** 🗺️  
   Users can add POIs along a trail (e.g., scenic views, water sources, picnic areas) with geographical locations.

4. **Trail Search** 🔍  
   Users can search for trails by geographic area, difficulty level, duration, and accessibility.

5. **Private Messaging** ✉️  
   Users can send private messages to others, for instance, to request more information about a specific trail.

6. **Inaccuracy Reports** ⚠️  
   Users can report inaccuracies about trails. A report includes a title and description, and trails with reports are marked with a warning.

7. **Admin Management** 🛠️  
   Admins can manage reports, view feedback from trail creators, and decide whether to keep or remove the trail.

8. **Promotional Emails** 📧  
   Admins can send promotional emails about hiking gear to all users.

## 🌐 System Architecture

NaTour21 implements a **three-tier architecture pattern**, with clients sending requests to the server, which handles data processing and database interactions securely.

### Backend:
- **Framework**: Spring Boot (Java)
- **REST API**: Spring Boot RESTful Web Services
- **Database Mapping**: Spring Boot JPA
- **Security**: Spring Boot Security for access control
- **Cloud Services**: 
  - AWS Elastic Beanstalk
  - AWS Cognito (Authentication)
  - AWS RDS (PostgreSQL Database)
  - AWS S3 (File Storage)

### Mobile Client:
- **Platform**: Android (Java)
- **Library**: Retrofit 2 for accessing REST services

## 🌩️ Cloud Infrastructure

NaTour21 uses **AWS Cloud** services to ensure performance and scalability, preparing for potential user growth:
- **Elastic Beanstalk** for deploying the back-end.
- **Cognito** for user authentication.
- **RDS** (PostgreSQL) for database management.
- **S3** for file storage.

## 🌟 Why NaTour21?

Hiking is not only a physical activity but also a way of connecting with nature and the wider community of outdoor enthusiasts. NaTour21 brings that connection into the digital space, making it easier than ever to share experiences, discover new trails, and plan adventures. Whether you want to explore new locations, find routes suitable for your abilities, or get advice from other hikers, NaTour21 is the perfect companion for every outdoor adventure 🏔️.

By leveraging modern cloud-based technologies and focusing on user-centered design, NaTour21 ensures that both beginner and experienced hikers have access to reliable, up-to-date, and easily accessible information.

---

Enjoy the adventure with **NaTour21**! 🌲🗻
