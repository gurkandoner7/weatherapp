Hello Everyone,  

I would like to share with you the weather application I developed using the OpenWeatherAPI services. The main topics I used in this application are as follows: 

MVVM 
Hilt 
Repository-Usecase 
MPAndroidCharts Library 
RoomDB 
Firebase Auth, Google 
Extension classes 
Flows 
Coroutines 
SharedPreferences 
ViewBinding 
Glide 
Utils 
CustomViews 
GridLayoutManager 
Chucker 
DataMapper 

I prioritized clean architecture in the application. In addition to Firebase Auth, the app also offers Google login as an option. It can retrieve real-time weather information for the user's current location and allows them to add new locations from the "locations" menu. The last location for which the service was called is stored in SharedPreferences, and the app retrieves the updated information for that location upon restart. Furthermore, newly added locations are stored in a RoomDB table and integrated with a RecyclerView, enabling the storage of added locations in the device's memory. Lastly, the "graph" screen features the MPAndroidCharts library, which I incorporated as a third-party component and customized accordingly. I used mock data to test the user interface of the graph and integrated it with a RecyclerView that displays hourly data for the selected days. 



 ![weather](https://github.com/gurkandoner7/weatherapp/assets/150553508/09dc85f3-514b-4286-afc9-f0df52977960)
