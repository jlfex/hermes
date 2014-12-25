<#list loans as l>  
					<tr >
					
						<td class="right">${l.id}</td>
						<td class="right">${l.loanNo}</td>
						<td class="right">${l.amount}</td>
						<td class="right">${l.rate}</td>
						<td class="right">${l.period}</td>
						<td class="right">${l.description}</td>
						
						<td class="right">
								<a href="${app}/loan/testSubmit/${l.id}" class="i_btn1 i_bg1">放款</a>
							
						</td>
					</tr>
					</#list>