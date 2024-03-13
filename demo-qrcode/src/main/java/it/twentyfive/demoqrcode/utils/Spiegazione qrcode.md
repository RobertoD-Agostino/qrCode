METODO PER COLORARE IL QRCODE
La matrice di bit viene utilizzata per rappresentare il modello del codice QR. Ogni bit nella matrice corrisponde a un modulo (punto) nel codice QR. I bit sono organizzati in una griglia che rappresenta il contenuto del codice QR stesso, inclusi dati e posizioni di riferimento. Questa matrice di bit è l'essenza del codice QR.
Per rendere visibile i dati rappresentati dai bit, la matrice viene convertita in un'immagine PNG
L'oggetto MatrixToImageConfig è utilizzato per specificare le opzioni di configurazione durante la conversione della matrice di bit in un'immagine. In questo contesto, viene utilizzato per definire il colore del codice QR e dello sfondo dell'immagine PNG risultante. Permette di personalizzare l'aspetto visivo del codice QR generato.
Nell'ultima riga prima del return il "MatrixToImageWriter.writeToStream" scrive l'immagine del qrcode basandosi sul contenuto della matrice di bit. L'immagine viene generata e memorizzata all'interno del "pngOutpurStream"



METODO PER AGGIUNGERE LA CORNICE
Prende in input l'immagine del codice QR sotto forma di un array di byte (qrCodeImage), i margini superiori e inferiori (topBorder e bottomBorder), i margini sinistro e destro (leftBorder e rightBorder), e il colore del bordo (borderColor). Il metodo restituisce un array di byte che rappresenta l'immagine del codice QR con il bordo aggiunto. Il metodo può sollevare un'eccezione di tipo IOException.
Prima convertiamo l'array di byte in un'immagine BufferedImage, poi calcoliamo le dimensioni della nuova immagine con il bordo aggiunto e creaimo un'altra immagine BufferedImage con le nuove dimensioni
Poi con Graphics2D disegnamo sull'immagine il bordo e il suo colore
Infine convertiamo l'immagine in un array di byte in formato PNG per tornarlo
BufferedImage: è una classe per la gestione delle immagini. Rappresenta un'immagine raster, ovvero un'immagine formata da una griglia di pixel. Permette di manipolare le immagini tramite "ImageIO"
ImageIO: è una classe Java che fornisce metodi per leggere e scrivere immagini in diversi formati.



METODO PER AGGIUNGERE TESTO ALLA CORNICE
Prende in input un array di byte rappresentante un'immagine del codice QR con il bordo (borderedQrCodeBytes), il testo da aggiungere (text), il font del testo (font), e il colore del testo (textColor). 
Prima viene convertito l'array di byte in un'immagine "BufferedImage", poi viene creato un oggetto "Graphics2D" per disegnare il testo sull'immagine. Vengono calcolate le dimensioni del testo utilizzando le informazioni sul font (larghezza, altezza)
Viene calcolata la posizione in cui verrà inserito il testo e viene poi disegnato tramite il metodo "drawString" e vengono liberate le risorse di "Graphics2D"

**Quando diciamo che viene "rilasciata la risorsa Graphics2D", ci riferiamo al fatto che stiamo liberando le risorse associate all'oggetto Graphics2D dopo aver terminato di utilizzarlo.

In Java, quando crei un oggetto Graphics2D per disegnare su un'immagine o su di un componente grafico, è importante liberare le risorse allocate da questo oggetto una volta che hai finito di utilizzarlo. Questo viene fatto chiamando il metodo dispose() sull'oggetto Graphics2D. Questo metodo rilascia le risorse di sistema allocate per l'oggetto Graphics2D, come la memoria, il contesto grafico e altri. Liberare queste risorse è importante per evitare perdite di memoria e garantire che le risorse siano disponibili per altri scopi.**

Infine l'immagine col testo aggiunto viene convertita in un array di byte in formato PNG per essere tornato.


