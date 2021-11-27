buildHistoryTable();

async function buildHistoryTable(){
	let data = await getEmployeeReimbursements();
	let table = document.getElementById('reimbursementTable')
	
	for(let i = 0; i < data.length; i++){
		let row = `<tr>
					<td>${data[i].id}</td>
					<td>${data[i].emp_id}</td>
					<td>$${data[i].amount}</td>
					<td>${data[i].type}</td>
					<td>${data[i].date}</td>
					<td>${data[i].status}</td>
				   </tr>`
			table.innerHTML += row
	}
}

async function getEmployeeReimbursements(){
    let id = window.localStorage.getItem('employeeId');
    try{
        let res = await fetch(`http://localhost:7000/api/employeerequests/${id}`)
        let data = res.json();
		console.log(data);
        return data;
    } catch (e) {
        throw (e.message);
    }
}