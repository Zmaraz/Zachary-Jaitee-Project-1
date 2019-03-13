// Dashboard functionality, loading of dashboard is done in app.ja
let userId = '';

function configureDashboard(){
    
    document.getElementById('dynamic-css').href = './css/dashboard.css';    
    
    document.getElementById('nav-dash-btn').addEventListener('click', mainDash);
    document.getElementById('nav-ticket-table-btn').addEventListener('click', getTickets);
    document.getElementById('nav-add-btn').addEventListener('click',createTicket);    
}

function mainDash(){
    console.log('in mainDash')
    clearBody();
}


function createTicket(){
    console.log('in createTicket()');

    clearBody();

    let createTicketArea = document.createElement('div');
    createTicketArea.setAttribute('id','createTicketArea');

    createTicketArea.innerHTML = `  
    <h1 class="h3 mb-3 font-weight-normal">Enter Your Reimbusement Information</h1>
        <div id="ticket-zone">           
            <input type="text" id="amount" name="amount" class="form-control" placeholder="enter Amount" required autofocus>
            <input type="text" id="description" name="description" class="form-control" placeholder="enter a short description" required autofocus>
            
                <select class="form-control" id="type">
                    <option value = "">Choose a Type</option>
                    <option value="FOOD">FOOD</option>
                    <option value="LODGING">LODGING</option>
                    <option value="TRAVEL">TRAVEL</option>
                    <option value="OTHER">OTHER</option>
                </select>

            
        <button class="btn btn-lg btn-primary btn-block" id="create-ticket-btn">Submit Ticket</button>
    </div><div id = "ticket-alert-msg">
    <p>Please fill out all fields with valid input</p>
</div>`;

    document.getElementById('dashboardBody').appendChild(createTicketArea);
    document.getElementById('ticket-alert-msg').hidden = true;
    document.getElementById('create-ticket-btn').addEventListener('click',onSubmitClick);

}

function onSubmitClick(){
    let ticketAmount = document.getElementById('amount').value;
    let ticketDescription = document.getElementById('description').value;
    let ticketType = document.getElementById('type').value;
    console.log(ticketType);
    
        let ticket= [];
        ticket.push('add');
        ticket.push(localStorage.getItem('uid'));
        ticket.push(ticketAmount);
        ticket.push(ticketType);
        ticket.push(ticketDescription);
        console.log(check());
        if(check()){
            document.getElementById('ticket-alert-msg').hidden = false;
        }else{
            document.getElementById('ticket-alert-msg').hidden = true;
            document.getElementById('create-ticket-btn').disabled = true;
            submitTicket(ticket);
        }            
    // }

}

async function submitTicket(ticket){
    console.log('in submitTicket')
    let response = await fetch('ticket', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwt')
        },
        body: JSON.stringify(ticket)
    });
    if(response.status == 200){
        console.log('ticket submitted sucessfully')
    }
    else{
        console.log(response.status);
    }
}

async function getTickets(){
    console.log('in getTickets()');
    clearBody();  
    let response = await fetch('ticket', {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')            
        }
    });    

    console.log('Headers has role: '+ response.headers.has('UserRole'));
    console.log('Headers has id: '+ response.headers.has('UserId'));
    let role = response.headers.get('UserRole');
    userId = response.headers.get('UserId');
    let body = await response.json();
    loadTable(body, role, userId);   
}


function loadTable(response, role, id){
    console.log('in loadtable');
    // console.log(response);
      

    //creates the table
    document.getElementById('dashboardBody').innerHTML=
    `<div class="table-responsive" id = "ticketTable">
    <h2>All Tickets</h2>
     <table class="table table-striped table-sm">
       <thead>
         <tr id = "tableHead">
           <th>ID</th>
           <th>Author</th>
           <th>Amount</th>
           <th>Type</th>
           <th>Description</th>
           <th>Submission Time</th>
           <th>Resolution Time</th>
           <th>Status</th>
         </tr>
       </thead>
       <tbody id="tablebody"></tbody>
     </table>
   </div>`;

    for(let i=0; i < response.length; i++){
        let newRow = document.createElement('tr');
        newRow.innerHTML = `
        <td>${response[i].reimbId}</td>
        <td>${response[i].authorId}</td>
        <td>${response[i].amount}</td>
        <td>${response[i].type}</td>
        <td>${response[i].ticketDescription}</td>
        <td>${response[i].timeSubmitted}</td>
        <td>${response[i].timeResolved}</td>
        <td>${response[i].status}</td>`;

        if(role === 'manager'){
            
            //checks that the response is pending and the logged in user did not make the ticket
            
            if(response[i].status == 'PENDING' && response[i].authorId != id){
                newRow.innerHTML += `<td><button id="ApproveButton${i}">Approve</button></td>
                <td><button id="DenyButton${i}">Deny</button></td>`;
            
            }
        }
        
        document.getElementById('tablebody').appendChild(newRow);
    }
    //add event listener and style class to buttons
    for(let i = 0; i < response.length; i++){
        if(document.getElementById(`ApproveButton${i}`)){
            document.getElementById(`ApproveButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`ApproveButton${i}`).setAttribute('class','btn btn-sm btn-outline-secondary');
            document.getElementById(`ApproveButton${i}`).setAttribute('value','APPROVED');
            document.getElementById(`ApproveButton${i}`).setAttribute('name',response[i].reimbId);
        }
        if(document.getElementById(`DenyButton${i}`)){
            document.getElementById(`DenyButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`DenyButton${i}`).setAttribute('class','btn btn-sm btn-outline-secondary');
            document.getElementById(`DenyButton${i}`).setAttribute('value','DENIED');
            document.getElementById(`DenyButton${i}`).setAttribute('name',response[i].reimbId);
        }
    }
}

async function updateTicket(e){
    console.log('in updateTicket');
    let btns = document.getElementsByName(e.target.name);
    for(let i = 0; i < btns.length; i++){
        btns[i].disabled = true;
    }
    
    let ticketData = []
    ticketData.push('update');
    ticketData.push('0') //author is
    ticketData.push(userId);//resolver id
    ticketData.push(e.target.name); //reimbId
    ticketData.push(e.target.value); //status
    console.log(ticketData);
    //["update","2","3","42","APPROVED"]
    
    let response = await fetch('ticket', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')            
        },
        body: JSON.stringify(ticketData)
    });  
    if(response.status == 200){
        getTickets();
    }
    
}

/*** HELPER FUNCTIONS */
function clearBody(){
    while (document.getElementById('dashboardBody').firstChild) {
        document.getElementById('dashboardBody').removeChild(document.getElementById('dashboardBody').firstChild);
    }
}