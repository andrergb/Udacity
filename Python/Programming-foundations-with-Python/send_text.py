from twilio.rest import Client

# Account SID and Auth Token from twilio.com/console
account_sid = "..."
auth_token  = "..."

client = Client(account_sid, auth_token)

message = client.messages.create(
    to="...", 
    from_="...",
    body="Hello from Python!")

print(message.sid)
