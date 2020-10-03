USE bookStoreTest;

INSERT INTO books (title, author, isInStock, publishDate, price, incomeDate, description)
VALUES (
	'Hotel', 
	'Arthur Henley', 
	TRUE, 
	'2018-01-01 00:00:00', 
	200.00, 
	'2020-05-23 00:00:00', 
	'The novel was adapted into a movie in 1967, and in 1983 Aaron Spelling turned it into a television series, airing for five years on ABC. In the TV series the St. Gregory Hotel was moved from New Orleans to San Francisco.'
),
(
	'Jane Eyre', 
	'Charlotte Bronte', 
	FALSE, 
	'2015-01-01 00:00:00', 
	350.00, 
	'2020-03-20 00:00:00', 
	'The novel revolutionised prose fiction by being the first to focus on its protagonist\'s moral and spiritual development through an intimate first-person narrative, where actions and events are coloured by a psychological intensity. Charlotte Bronte has been called the ""first historian of the private consciousness"", and the literary ancestor of writers like Proust and Joyce.'
),
(
	'Solaris', 
	'Stanislaw Lem', 
	TRUE, 
	'2019-01-01 00:00:00', 
	210.00, 
	'2019-09-10 00:00:00', 
	'A team of human scientists is probing and examining the oceanic surface of the planet Solaris from a hovering research station. Solaris manifests an ability to cast their secret, guilty concerns into a material form for each scientist to personally confront. All human efforts to make sense of Solaris\' activities ultimately prove futile. As Lem wrote, ""The peculiarity of those phenomena seems to suggest that we observe a kind of rational activity, but the meaning of this seemingly rational activity of the Solarian Ocean is beyond the reach of human beings"". He also wrote that he deliberately chose the ocean as a sentient alien to avoid any personification and the pitfalls of anthropomorphism in depicting first contact.'
),
(
	'Animal Farm', 
	'George Orwell', 
	TRUE, 
	'2017-01-01 00:00:00', 
	180.00, 
	'2020-03-04 00:00:00', 
	'The book tells the story of a group of farm animals who rebel against their human farmer, hoping to create a society where the animals can be equal, free, and happy. Ultimately, however, the rebellion is betrayed, and the farm ends up in a state as bad as it was before, under the dictatorship of a pig named Napoleon.'
),
(
	'Three men in the boat', 
	'Jerome K. Jerome', 
	FALSE, 
	'2016-03-15 00:00:00', 
	100.00, 
	'2020-03-04 00:00:00', 
	'The book was initially intended to be a serious travel guide, with accounts of local history along the route, but the humorous elements took over to the point where the serious and somewhat sentimental passages seem a distraction to the comic novel. One of the most praised things about Three Men in a Boat is how undated it appears to modern readers – the jokes have been praised as fresh and witty.'
),
(
	'The Call of Cthulhu', 
	'Howard Lovecraft', 
	TRUE, 
	'2018-01-01 00:00:00', 
	160.00, 
	'2020-03-04 00:00:00', 
	'It represented a monster of vaguely anthropoid outline, but with an octopus-like head whose face was a mass of feelers, a scaly, rubbery-looking body, prodigious claws on hind and fore feet, and long, narrow wings behind.'
),
(
	'War and Peace', 
	'Lev Tolstoy', 
	FALSE, 
	'2010-01-01 00:00:00', 
	350.00, 
	'2020-03-04 00:00:00', 
	'War and Peace broadly focuses on Napoleon\'s invasion of Russia in 1812 and follows three of the most well-known characters in literature.'
),
(
	'Crime and Punishment', 
	'Feodor Dostoevsky', 
	TRUE, 
	'2015-01-01 00:00:00', 
	350.00, 
	'2020-05-23 00:00:00', 
	'Crime and Punishment is considered the first great novel of his ""mature"" period of writing. ... Crime and Punishment focuses on the mental anguish and moral dilemmas of Rodion Raskolnikov, an impoverished ex-student in Saint Petersburg who formulates a plan to kill an unscrupulous pawnbroker for her money.'
),
(
	'An attack on nerves', 
	'Anton Chekhov', 
	TRUE, 
	'2015-01-01 00:00:00', 
	200.00, 
	'2019-01-10 00:00:00', 
	'A night on the town with two friends turns into ""an attack of nerves"" for Vasilyev, a law student. The three students spend the night drinking and visiting houses of prostitution; Vasilyev is horrified and repulsed by the women, who he thinks are ""more like animals than human beings."" The social problem of prostitution becomes an obsession; he is so fixated on finding a solution that he is in moral agony. His friends, among whom is a medical student, are concerned only with his health; they take him to a psychiatrist who ""cures"" Vasilyev with bromide and morphine.'
),
(
	'Flowers for Algernon', 
	'Daniel Keyes', 
	TRUE, 
	'2018-01-11 00:00:00', 
	200.00, 
	'2020-05-10 00:00:00', 
	'In Flowers for Algernon, the mentally handicapped Charlie Gordon is transformed by a surgery that allows him to become intelligent. The short story and later-developed novel explores themes about the cycle of life, the limits of science, and whether knowledge is truly more valuable than happiness.'
);

INSERT INTO orders (customerName, customerMail, closedDate, status, totalPrice)
VALUES (
	'Sergey', 'sergeymail@mail.fu', null, 'NEW', 200.00
),
(
	'Maria', 'mariyamail@gmail.su', '2020-08-21 17:00:00', 'CLOSED', 100.00
),
(
	'Sasha', 'sashkinmail@mail.ru', null, 'CANCELLED', 350.00
);

INSERT INTO orderBooks (orderId, bookId)
VALUES (1, 1), (2, 5), (3, 7);

INSERT INTO requests (dateCreated, isClosed, bookId)
VALUES (
	'2020-08-22 17:00:00', FALSE, 2
),
(
	'2020-08-21 17:00:00', FALSE, 5
);