<!DOCTYPE html>
<html>
<head>
    <title>Jersey RESTful Web Application</title>
    <script>
        async function callGetMsg2() {
            // Get the input value
            const inputValue = document.getElementById("inputValue").value;

            // Create the JSON object
            const jsonData = JSON.stringify({ message: inputValue });

            console.log('Input Value:', inputValue);
            console.log('JSON Data:', jsonData);

            try {
                // Make the AJAX request
                const response = await fetch('webapi/myresource/getMsg2', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: jsonData
                });

                // Check if the response is OK (status in the range 200-299)
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }

                const data = await response.json();
                // Display the response
                document.getElementById("response").innerText = data.response;
            } catch (error) {
                console.error('Error:', error);
                document.getElementById("response").innerText = 'Error: ' + error.message;
            }
        }
    </script>
</head>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="webapi/myresource/getMsg">Jersey resource1</a></p>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a> for more information on Jersey!</p>

    <h3>Call getMsg2 API</h3>
    <input type="text" id="inputValue" placeholder="Enter your message" />
    <button onclick="callGetMsg2()">Send</button>

    <h4>Response:</h4>
    <p id="response"></p>

    <script>
        // Optional: Log any errors in the console
        window.onerror = function(message, source, lineno, colno, error) {
            console.error('Error occurred: ', message, ' at ', source, ':', lineno, ':', colno);
        };
    </script>
</body>
</html>