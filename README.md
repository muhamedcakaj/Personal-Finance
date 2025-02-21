**Projekti: Menaxhimi i Financave Personale**

Ky projekt ofron mjete për menaxhimin e financave personale për përdoruesit. Funksionalitetet kryesore përshijnë:

### Funksionalitetet Kryesore:
1. **Regjistrimi dhe Kyçja:**
   - Përdoruesit mund të regjistrohen dhe të kyçen në llogaritët e tyre.

2. **Menaxhimi i Shpenzimeve:**
   - Shtimi i shpenzimeve me kategori, arsye dhe shumë.
   - Rritja e balancit personal.

3. **Profili i Përdoruesit:**
   - Mundëson ndryshimin dhe menaxhimin e të dhënave personale.

4. **Për Administratën:**
   - Dashboard me statistika dhe informata mbi aplikacionin.
   - CRUD operacione për menaxhimin e përdoruesve.

### Teknologjitë dhe Praktikat e Përdorura:
1. **Mikroshërbime:**
   - Shërbime të vogla dhe të pavarura që komunikojnë përmes API-ve, duke mundësuar zhvillim dhe shkallëzim të lehtë.
     
2. **Autentifikimi dhe Autorizimi:**
   - Siguri përmes fjalëkalimeve dhe roleve të ndryshme për qasje.
   - Verifikimi i email-it përmes një kodi që dërgohet në email-in e përdoruesit pas login. 
   - Gjenerimi i JWT token pas verifikimit të suksesshëm, i cili përmban *username* dhe *role*. 
   - Skadimi i token-it pas një kohe të caktuar dhe kërkesa për rilogim. 

3. **Caching:**
   - Ruajtje e të dhënave në memorie (p.sh., Redis) për përmirësimin e performancës.

4. **Load Balancing:**
   - Shpërndarje e trafikut mes serverëve për shmangien e mbingarkesës.
   - Përdorimi i Eureka Server për shpërndarjen dinamike të kërkesave ndërmjet instanceve të mikroshërbimeve. 

5. **Swagger UI:**
   - Dokumentacion interaktiv për API-të.

6. **Monitorimi:**
   - Përdorimi i mjeteve si Grafana dhe Prometheus për të ndjekur performancën.

7. **API Gateway:**
   - Menaxhimi i kërkesave dhe autentifikimi përmes një komponenti të dedikuar (Spring Cloud Gateway). 
   - Validimi i JWT token-it dhe kontrolli i roleve të përdoruesit (autorizim). 

8. **Testimi:**
   - Teste automatike për sigurimin e funksionalitetit korrekt.

9. **Teknologjitë e Përdorura:**
   - **SQL Server:** Për ruajtjen dhe menaxhimin e të dhënave.
   - **Java Spring Boot:** Për zhvillimin e pjesës backend.
   - **ReactJS:** Për zhvillimin e pjesës frontend.
  
![LoginPage](https://github.com/user-attachments/assets/7daa8ed7-7a62-4dd7-811f-85ac9cfbf140)
![SingupPage](https://github.com/user-attachments/assets/e713749b-abea-42d3-b33b-6b306741489f)
![VerificationCode](https://github.com/user-attachments/assets/e5c69f97-49cd-4513-ac60-1a2cb9390663)
![UserHome](https://github.com/user-attachments/assets/cb1abb8a-1e64-47e6-a093-ac6286fb9b07)
![UserAddCash](https://github.com/user-attachments/assets/6baa0bb5-6928-47c4-beb3-d9fe56d3e26e)
![UserProfile](https://github.com/user-attachments/assets/040aaa93-23d6-4f50-8299-3d87de6630d1)
![UserLogout](https://github.com/user-attachments/assets/77464785-e9ff-478f-9a14-ea5a2ed7b8eb)
![AdminCrud](https://github.com/user-attachments/assets/e0c40008-5fcb-47a4-bc80-ff84f8dd2711)
![AdminHome](https://github.com/user-attachments/assets/e7e47c1d-3d63-45ce-948d-32c54fafb7b7)
![AdminEdit](https://github.com/user-attachments/assets/533f04c4-8152-4966-80fe-4cbbb5e846ee)
![AdminDelete](https://github.com/user-attachments/assets/38b93b01-b943-4813-9fb4-a391fadf1cfc)
![AdminAdd](https://github.com/user-attachments/assets/95ae2aef-c9c2-4a44-9c23-8a7ddad08d56)












