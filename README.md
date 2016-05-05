# Search
Android app which shows you the venues to visit/eat/enjoy at any place.

<h3>Approach</h3>

The following steps have been taken for the development:

Making the app structure with the necessary application screens.<br/>
Integrating the API and fetching the data.<br/>
Data population in proper fields.<br/>
Incorporating workflow.<br/>

<h3>Design & Utility</h3>

The application screens are:

<b>Home:</b>This screen asks the user a valid location to start with. Blank and invalid entry are not allowed. Once a valid location is entered it moves to the next screen.<br/>
<b>Venue List:</b> This screen displays all the venues to visit/eat/enjoy after fetching the values from the api.<br/>
<b>Venue Details:</b> In this screen the venue details of the selected venue is shown.<br/>

<h3>Code Layout</h3>

The following classes contains the workflow:

<b>ServiceHandler:</b> This class has the encapsulated request handlers for posing the GET, POST requests.<br/>

<b>MainActivity:</b> This activity is responsible for the Home screen functionalities. It is holding the connectivity information and using the methods from ServiceHandler. It is creating the api connectivity through a inner class named GetVenues which is an AsyncTask.<br/>

<b>VenueListActivity:</b> This activity is responsible for displaying the list of venues in a location. Once any item is clicked it moves to the next screen.<br/>

<b>SingleVenueActivity:</b> This activity shows the details of each venue like its address, rating, image if available and also link to its website.<br/>
	
	
